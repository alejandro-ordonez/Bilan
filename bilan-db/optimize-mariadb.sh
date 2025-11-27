#!/bin/bash

################################################################################
# MariaDB Performance Optimization Script for RHEL 9 / CentOS
#
# This script calculates optimal MariaDB settings based on available RAM
# and generates a configuration file for systemd-managed MariaDB.
#
# Usage:
#   sudo ./optimize-mariadb.sh [--ram-gb <value>]
#
# Options:
#   --ram-gb <N>    Override RAM detection (optional)
#
# Examples:
#   sudo ./optimize-mariadb.sh
#   sudo ./optimize-mariadb.sh --ram-gb 32
#
# Requirements:
#   - RHEL 9 or CentOS
#   - MariaDB installed via yum
#   - Run as root or with sudo
################################################################################

set -e

# Color codes for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Default values
RAM_OVERRIDE=""

# Parse arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        --ram-gb)
            RAM_OVERRIDE="$2"
            shift 2
            ;;
        -h|--help)
            echo "Usage: $0 [--ram-gb <value>]"
            echo ""
            echo "Options:"
            echo "  --ram-gb <N>    Override RAM detection (optional)"
            echo "  -h, --help      Show this help message"
            exit 0
            ;;
        *)
            echo -e "${RED}Error: Unknown option: $1${NC}"
            echo "Usage: $0 [--ram-gb <value>]"
            exit 1
            ;;
    esac
done

# Check if running as root
if [ "$EUID" -ne 0 ]; then
    echo -e "${RED}Error: This script must be run as root${NC}"
    echo "Please run: sudo $0"
    exit 1
fi

################################################################################
# Detect or use provided RAM
################################################################################

if [ -n "$RAM_OVERRIDE" ]; then
    TOTAL_RAM_GB=$RAM_OVERRIDE
    echo -e "${GREEN}Using provided RAM: ${TOTAL_RAM_GB}GB${NC}"
else
    # Detect total RAM in GB
    if [ -f /proc/meminfo ]; then
        TOTAL_RAM_KB=$(grep MemTotal /proc/meminfo | awk '{print $2}')
        TOTAL_RAM_GB=$(awk "BEGIN {printf \"%.0f\", $TOTAL_RAM_KB / 1024 / 1024}")
    else
        echo -e "${RED}Error: Cannot detect RAM from /proc/meminfo${NC}"
        echo "Please use --ram-gb option to specify RAM manually."
        exit 1
    fi
    echo -e "${GREEN}Detected RAM: ${TOTAL_RAM_GB}GB${NC}"
fi

# Validate RAM
if [ "$TOTAL_RAM_GB" -lt 2 ]; then
    echo -e "${RED}Error: Insufficient RAM detected (${TOTAL_RAM_GB}GB). Minimum 2GB required for production.${NC}"
    exit 1
fi

################################################################################
# Detect CPU cores
################################################################################

if [ -f /proc/cpuinfo ]; then
    CPU_CORES=$(grep -c ^processor /proc/cpuinfo)
    echo -e "${GREEN}Detected CPU cores: ${CPU_CORES}${NC}"
else
    CPU_CORES=4
    echo -e "${YELLOW}Warning: Cannot detect CPU cores. Using default: ${CPU_CORES}${NC}"
fi

################################################################################
# Check MariaDB installation
################################################################################

if ! command -v mariadb &> /dev/null && ! command -v mysql &> /dev/null; then
    echo -e "${RED}Error: MariaDB/MySQL client not found${NC}"
    echo "Please install MariaDB first: sudo yum install mariadb-server"
    exit 1
fi

# Check if MariaDB is installed and get version
if systemctl list-unit-files | grep -q mariadb.service; then
    MARIADB_SERVICE="mariadb"
elif systemctl list-unit-files | grep -q mysql.service; then
    MARIADB_SERVICE="mysql"
else
    echo -e "${YELLOW}Warning: MariaDB service not found in systemd${NC}"
    echo "Configuration will still be generated."
    MARIADB_SERVICE="mariadb"
fi

################################################################################
# Calculate optimal values based on available RAM
################################################################################

echo ""
echo "Calculating optimal settings..."
echo ""

# InnoDB Buffer Pool Size (60% of total RAM for dedicated DB server)
# For mixed-use servers, consider reducing to 40-50%
BUFFER_POOL_PERCENT=60
BUFFER_POOL_GB=$(awk "BEGIN {printf \"%.0f\", $TOTAL_RAM_GB * $BUFFER_POOL_PERCENT / 100}")

# Ensure minimum 512MB and reasonable size
if [ "$BUFFER_POOL_GB" -lt 1 ]; then
    BUFFER_POOL_SIZE="512M"
    BUFFER_POOL_INSTANCES=1
else
    BUFFER_POOL_SIZE="${BUFFER_POOL_GB}G"
    # 1 instance per GB, min 1, max 64
    BUFFER_POOL_INSTANCES=$BUFFER_POOL_GB
    if [ "$BUFFER_POOL_INSTANCES" -gt 64 ]; then
        BUFFER_POOL_INSTANCES=64
    fi
    if [ "$BUFFER_POOL_INSTANCES" -lt 1 ]; then
        BUFFER_POOL_INSTANCES=1
    fi
fi

# Max connections based on RAM
# Formula: ~15 connections per GB of RAM, with reasonable limits
MAX_CONNECTIONS=$(awk "BEGIN {printf \"%.0f\", $TOTAL_RAM_GB * 15}")
if [ "$MAX_CONNECTIONS" -lt 150 ]; then
    MAX_CONNECTIONS=150
elif [ "$MAX_CONNECTIONS" -gt 1000 ]; then
    MAX_CONNECTIONS=1000
fi

# InnoDB Log File Size (8-10% of buffer pool, max 2GB)
LOG_FILE_SIZE_MB=$(awk "BEGIN {printf \"%.0f\", $BUFFER_POOL_GB * 1024 * 0.08}")
if [ "$LOG_FILE_SIZE_MB" -lt 256 ]; then
    LOG_FILE_SIZE_MB=256
elif [ "$LOG_FILE_SIZE_MB" -gt 2048 ]; then
    LOG_FILE_SIZE_MB=2048
fi
LOG_FILE_SIZE="${LOG_FILE_SIZE_MB}M"

# InnoDB Log Buffer Size (proportional to RAM)
if [ "$TOTAL_RAM_GB" -lt 4 ]; then
    LOG_BUFFER_SIZE="8M"
elif [ "$TOTAL_RAM_GB" -lt 16 ]; then
    LOG_BUFFER_SIZE="16M"
elif [ "$TOTAL_RAM_GB" -lt 32 ]; then
    LOG_BUFFER_SIZE="32M"
else
    LOG_BUFFER_SIZE="64M"
fi

# Thread cache size (10-15% of max_connections)
THREAD_CACHE_SIZE=$(awk "BEGIN {printf \"%.0f\", $MAX_CONNECTIONS * 0.12}")
if [ "$THREAD_CACHE_SIZE" -lt 50 ]; then
    THREAD_CACHE_SIZE=50
elif [ "$THREAD_CACHE_SIZE" -gt 200 ]; then
    THREAD_CACHE_SIZE=200
fi

# I/O threads (scale with CPU cores)
if [ "$CPU_CORES" -ge 16 ]; then
    IO_THREADS=16
elif [ "$CPU_CORES" -ge 8 ]; then
    IO_THREADS=8
elif [ "$CPU_CORES" -ge 4 ]; then
    IO_THREADS=4
else
    IO_THREADS=2
fi

# I/O capacity based on storage type (assume SSD for modern servers)
if [ "$TOTAL_RAM_GB" -ge 32 ]; then
    IO_CAPACITY=2000
    IO_CAPACITY_MAX=4000
elif [ "$TOTAL_RAM_GB" -ge 16 ]; then
    IO_CAPACITY=1000
    IO_CAPACITY_MAX=2000
else
    IO_CAPACITY=500
    IO_CAPACITY_MAX=1000
fi

# Temp table sizes (scale with RAM)
if [ "$TOTAL_RAM_GB" -lt 8 ]; then
    TMP_TABLE_SIZE="32M"
elif [ "$TOTAL_RAM_GB" -lt 16 ]; then
    TMP_TABLE_SIZE="64M"
elif [ "$TOTAL_RAM_GB" -lt 32 ]; then
    TMP_TABLE_SIZE="128M"
else
    TMP_TABLE_SIZE="256M"
fi

# Table cache sizes
TABLE_OPEN_CACHE=$(awk "BEGIN {printf \"%.0f\", $MAX_CONNECTIONS * 10}")
if [ "$TABLE_OPEN_CACHE" -lt 2000 ]; then
    TABLE_OPEN_CACHE=2000
elif [ "$TABLE_OPEN_CACHE" -gt 8192 ]; then
    TABLE_OPEN_CACHE=8192
fi

TABLE_DEFINITION_CACHE=$(awk "BEGIN {printf \"%.0f\", $TABLE_OPEN_CACHE / 2}")

# Recommended HikariCP pool size (30-40% of max_connections)
HIKARI_POOL_SIZE=$(awk "BEGIN {printf \"%.0f\", $MAX_CONNECTIONS * 0.35}")

################################################################################
# Display calculated values
################################################################################

echo "=========================================="
echo "Calculated MariaDB Settings"
echo "=========================================="
echo "System RAM:                   ${TOTAL_RAM_GB}GB"
echo "CPU Cores:                    ${CPU_CORES}"
echo ""
echo "Buffer Pool Size:             ${BUFFER_POOL_SIZE} (${BUFFER_POOL_PERCENT}% of RAM)"
echo "Buffer Pool Instances:        ${BUFFER_POOL_INSTANCES}"
echo "Max Connections:              ${MAX_CONNECTIONS}"
echo "InnoDB Log File Size:         ${LOG_FILE_SIZE}"
echo "InnoDB Log Buffer Size:       ${LOG_BUFFER_SIZE}"
echo "Thread Cache Size:            ${THREAD_CACHE_SIZE}"
echo "I/O Threads:                  ${IO_THREADS}"
echo "I/O Capacity:                 ${IO_CAPACITY} / ${IO_CAPACITY_MAX}"
echo "Temp Table Size:              ${TMP_TABLE_SIZE}"
echo "Table Open Cache:             ${TABLE_OPEN_CACHE}"
echo "Table Definition Cache:       ${TABLE_DEFINITION_CACHE}"
echo ""
echo "Recommended HikariCP Pool:    ${HIKARI_POOL_SIZE}"
echo "=========================================="
echo ""

################################################################################
# Generate configuration file
################################################################################

OUTPUT_FILE="/etc/my.cnf.d/zz-performance.cnf"

echo -e "${GREEN}Generating configuration file: ${OUTPUT_FILE}${NC}"
echo ""

cat > "$OUTPUT_FILE" <<EOF
# MariaDB Performance Configuration
# Generated by optimize-mariadb.sh
# Generated on: $(date)
# System: RHEL 9 / CentOS
# System RAM: ${TOTAL_RAM_GB}GB
# CPU Cores: ${CPU_CORES}

[mysqld]

################################################################################
# Connection Settings
################################################################################

# Maximum number of concurrent connections
max_connections = ${MAX_CONNECTIONS}

# Thread cache for connection reuse
thread_cache_size = ${THREAD_CACHE_SIZE}

# Connection timeout settings (in seconds)
connect_timeout = 10
wait_timeout = 600
interactive_timeout = 600

# Prevent IP blocking after failed connections
max_connect_errors = 1000

################################################################################
# InnoDB Buffer Pool (Most Important Setting)
################################################################################

# Main memory cache for data and indexes (${BUFFER_POOL_PERCENT}% of RAM)
# This is the single most important performance setting
innodb_buffer_pool_size = ${BUFFER_POOL_SIZE}

# Split buffer pool for better concurrency
innodb_buffer_pool_instances = ${BUFFER_POOL_INSTANCES}

# Each table gets its own file (better for performance/maintenance)
innodb_file_per_table = 1

################################################################################
# InnoDB Log Files
################################################################################

# Size of transaction log files
innodb_log_file_size = ${LOG_FILE_SIZE}

# Buffer for transaction logs before disk write
innodb_log_buffer_size = ${LOG_BUFFER_SIZE}

# Flush strategy for transaction logs
# 0 = flush every second (fastest, risk on MySQL crash)
# 1 = flush every transaction (safest, slowest)
# 2 = flush to OS cache every second (recommended balance)
innodb_flush_log_at_trx_commit = 2

# Bypass OS cache for data files (reduces double buffering)
# Use O_DIRECT on most systems, fdatasync on systems without O_DIRECT support
innodb_flush_method = O_DIRECT

################################################################################
# InnoDB I/O Settings
################################################################################

# Number of I/O threads for read/write operations
innodb_write_io_threads = ${IO_THREADS}
innodb_read_io_threads = ${IO_THREADS}

# Background I/O operations capacity (assumes SSD)
# Reduce to 200/400 if using traditional spinning disks
innodb_io_capacity = ${IO_CAPACITY}
innodb_io_capacity_max = ${IO_CAPACITY_MAX}

# Purge threads for cleaning up old row versions
innodb_purge_threads = 4

################################################################################
# Query Cache (Disabled - deprecated and causes contention)
################################################################################

query_cache_size = 0
query_cache_type = 0

################################################################################
# Temporary Tables
################################################################################

# Size of in-memory temporary tables
tmp_table_size = ${TMP_TABLE_SIZE}
max_heap_table_size = ${TMP_TABLE_SIZE}

# Per-connection buffers for joins and sorts
join_buffer_size = 2M
sort_buffer_size = 2M
read_buffer_size = 1M
read_rnd_buffer_size = 1M

################################################################################
# Binary Logging
################################################################################

# Binary logging is enabled by default for point-in-time recovery
# Uncomment the next line to disable if not needed (saves I/O overhead)
# skip-log-bin

# If binary logging is enabled, optimize it
binlog_cache_size = 1M
max_binlog_size = 100M

# Sync binary log to disk (0 = let OS handle it, faster but less safe)
sync_binlog = 0

################################################################################
# Performance Schema
################################################################################

# Performance schema for monitoring (as requested, kept ON)
performance_schema = ON

# Limit performance schema memory usage
performance_schema_max_table_instances = 500
performance_schema_max_table_handles = 1000

################################################################################
# Table and Schema Settings
################################################################################

# Table open cache (number of open tables cached)
table_open_cache = ${TABLE_OPEN_CACHE}

# Table definition cache (cached .frm files)
table_definition_cache = ${TABLE_DEFINITION_CACHE}

# Maximum packet size for client/server communication
max_allowed_packet = 64M

################################################################################
# Logging
################################################################################

# Slow query log for performance analysis
slow_query_log = 1
slow_query_log_file = /var/log/mariadb/slow-query.log
long_query_time = 2

# Don't log queries that don't use indexes (too noisy for large systems)
log_queries_not_using_indexes = 0

# Error log location
log_error = /var/log/mariadb/error.log

################################################################################
# Character Set (UTF-8)
################################################################################

character-set-server = utf8mb4
collation-server = utf8mb4_unicode_ci

################################################################################
# Network Settings
################################################################################

# Skip name resolution for better connection performance
skip-name-resolve

# Maximum packet size
max_allowed_packet = 64M

################################################################################
# Security
################################################################################

# Disable LOAD DATA LOCAL INFILE for security
local_infile = 0

EOF

echo -e "${GREEN}Configuration file created successfully!${NC}"
echo ""

################################################################################
# Create log directory if it doesn't exist
################################################################################

LOG_DIR="/var/log/mariadb"
if [ ! -d "$LOG_DIR" ]; then
    echo "Creating log directory: $LOG_DIR"
    mkdir -p "$LOG_DIR"
    chown mysql:mysql "$LOG_DIR"
    chmod 755 "$LOG_DIR"
fi

################################################################################
# Backup existing configuration if present
################################################################################

if systemctl is-active --quiet "$MARIADB_SERVICE"; then
    BACKUP_DIR="/root/mariadb-backup-$(date +%Y%m%d-%H%M%S)"
    mkdir -p "$BACKUP_DIR"

    echo -e "${YELLOW}Creating backup of current configuration...${NC}"
    cp -r /etc/my.cnf.d/*.cnf "$BACKUP_DIR/" 2>/dev/null || true
    cp /etc/my.cnf "$BACKUP_DIR/" 2>/dev/null || true

    echo -e "${GREEN}Backup created in: $BACKUP_DIR${NC}"
    echo ""
fi

################################################################################
# Instructions and recommendations
################################################################################

echo "=========================================="
echo "Next Steps"
echo "=========================================="
echo ""
echo "1. Review the configuration file:"
echo "   cat $OUTPUT_FILE"
echo ""
echo "2. Restart MariaDB to apply changes:"
echo "   sudo systemctl restart $MARIADB_SERVICE"
echo ""
echo "3. Check MariaDB status:"
echo "   sudo systemctl status $MARIADB_SERVICE"
echo ""
echo "4. Verify settings were applied:"
echo "   sudo mariadb -e \"SHOW VARIABLES LIKE 'innodb_buffer_pool_size';\""
echo "   sudo mariadb -e \"SHOW VARIABLES LIKE 'max_connections';\""
echo ""
echo "5. Monitor MariaDB logs for any errors:"
echo "   sudo tail -f /var/log/mariadb/error.log"
echo ""

echo "=========================================="
echo "Application Configuration Updates"
echo "=========================================="
echo ""
echo "Update your application.properties with these values:"
echo ""
echo "spring.datasource.hikari.maximum-pool-size=${HIKARI_POOL_SIZE}"
echo "spring.datasource.hikari.minimum-idle=10"
echo "spring.datasource.hikari.connection-timeout=30000"
echo "spring.datasource.hikari.idle-timeout=600000"
echo "spring.datasource.hikari.max-lifetime=1800000"
echo ""

echo "=========================================="
echo "Performance Monitoring"
echo "=========================================="
echo ""
echo "After restart, monitor these metrics:"
echo ""
echo "1. Buffer pool efficiency (should be > 99%):"
echo "   sudo mariadb -e \"SHOW STATUS LIKE 'Innodb_buffer_pool_read%';\""
echo ""
echo "2. Connection usage:"
echo "   sudo mariadb -e \"SHOW STATUS LIKE 'Threads_connected';\""
echo "   sudo mariadb -e \"SHOW STATUS LIKE 'Max_used_connections';\""
echo ""
echo "3. Slow queries:"
echo "   sudo tail -f /var/log/mariadb/slow-query.log"
echo ""
echo "4. System resources:"
echo "   htop"
echo "   iostat -x 1"
echo ""

echo "=========================================="
echo "Important Notes"
echo "=========================================="
echo ""
echo "• This configuration assumes a dedicated database server"
echo "• If MariaDB shares resources with other services, consider"
echo "  reducing innodb_buffer_pool_size to 40-50% of RAM"
echo ""
echo "• The current configuration uses ${BUFFER_POOL_PERCENT}% of RAM (${BUFFER_POOL_SIZE})"
echo "  for the InnoDB buffer pool"
echo ""
echo "• If using SELinux, ensure it allows MariaDB to write logs:"
echo "  sudo setsebool -P mysqld_connect_any 1"
echo ""
echo "• Consider setting up regular backups before making changes"
echo ""

echo "=========================================="
echo "Configuration Complete!"
echo "=========================================="
echo ""
echo -e "${GREEN}Ready to restart MariaDB with optimized settings.${NC}"
echo ""
