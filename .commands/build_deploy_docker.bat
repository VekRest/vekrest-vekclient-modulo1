@echo off
setlocal enabledelayedexpansion

echo ==========================================
echo CONFIGURACOES INICIAIS
echo ==========================================
echo.

set MODULE=vekclient
echo Modulo definido: %MODULE%
echo.

echo CRIANDO PASTA DE LOGS
IF NOT EXIST "logs-bat" (
    mkdir "logs-bat"
)

echo.

echo ==========================================
echo           BUILD DO MODULO
echo ==========================================

echo ===== PROCESSANDO MODULO: %MODULE% =====
echo.

echo ===========================
echo       MAVEN BUILD
echo ===========================

echo Executando Maven no modulo %MODULE%...


echo Maven OK para %MODULE%
echo.


echo ===========================
echo       DOCKER BUILD
echo ===========================

echo Excluindo imagem Docker antiga: vekrest/%MODULE%:latest
docker rmi vekrest/%MODULE%:latest

echo Construindo Nova Imagem Docker: vekrest/%MODULE%:latest
docker build -t vekrest/%MODULE%:latest ..\Dockerfile ..\ >> .commands/logs-bat/logs-docker-%MODULE%.txt 2>&1

if errorlevel 1 (
    echo [ERRO] Docker build falhou no módulo %MODULE%
    pause
    call :error_exit
)

echo Docker OK para %MODULE%
echo.

call :success_exit


:error_exit
echo.
echo ==========================================
echo   ERRO DURANTE O BUILD DO MODULO
echo ==========================================
call :wait_and_exit


:success_exit
echo.
echo ==========================================
echo     MODULO %MODULE% CONSTRUIDO COM SUCESSO
echo ==========================================
call :wait_and_exit


:wait_and_exit
echo.
echo A janela será fechada em 30 segundos...
pause
exit
