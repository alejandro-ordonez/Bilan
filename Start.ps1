$outputPath = './target/bilan-backend-1.jar'

if (-not [Environment]::GetEnvironmentVariable('JAVA_HOME')){
    Write-Output 'JAVA_HOME not set' ;
    exit;
}


if(-not [System.IO.File]::Exists($outputPath)){
    Write-Warning 'Jar not compiled'
    ./Build.ps1
}

$env:BILAN_SIMAT_URL='http://wsstandardsimatcert.mineducacion.gov.co/wsstandardsimat';
$env:BILAN_SINEB_URL='http://wsstandardsinebcert.mineducacion.gov.co/wsstandardsineb';
$env:JWT_SECRET='bilan-secret-jwt-2021';
$env:SERVER_PORT='8080';
$env:SPRING_PROFILES_ACTIVE='dev';


java -jar $outputPath
