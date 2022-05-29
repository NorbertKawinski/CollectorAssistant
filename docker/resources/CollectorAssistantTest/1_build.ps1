$name = (Get-Item -Path ".\").Name
$host.ui.RawUI.WindowTitle = "$name (build)"

docker build -t CollectorAssistant .

Read-Host "[OK] Done"