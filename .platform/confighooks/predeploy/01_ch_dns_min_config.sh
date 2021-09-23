#!/bin/bash
echo "Configuring simat dns"
echo 10.200.150.147 'wsstandardsimatcert.mineducacion.gov.co' | sudo tee -a /etc/hosts

echo "Configuring sineb dns"
echo 10.200.150.150 'wsstandardsinebcert.mineducacion.gov.co' | sudo tee -a /etc/hosts