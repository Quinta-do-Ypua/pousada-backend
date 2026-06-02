# SonarQube Setup

Este projeto está configurado para análise de código com SonarQube.

## Pré-requisitos

- Docker (para executar o servidor SonarQube localmente)
- Maven

## Executando o SonarQube Localmente

### 1. Iniciar o servidor SonarQube com Docker

```bash
docker run -d --name sonarqube -p 9000:9000 sonarqube:lts
```

Aguarde alguns segundos para o servidor iniciar. Acesse http://localhost:9000

**Credenciais padrão:**
- Usuário: `admin`
- Senha: `admin`

Na primeira vez que acessar, você será solicitado a alterar a senha.

### 2. Executar a análise SonarQube

Execute o seguinte comando na raiz do projeto:

```bash
mvn clean verify sonar:sonar
```

Este comando irá:
1. Compilar o projeto
2. Executar os testes
3. Gerar o relatório de cobertura do Jacoco
4. Enviar a análise para o SonarQube

## Configuração

As configurações do SonarQube estão no `pom.xml`:

```xml
<properties>
    <sonar.projectKey>PousadaBackend</sonar.projectKey>
    <sonar.organization>default-organization</sonar.organization>
    <sonar.host.url>http://localhost:9000</sonar.host.url>
    <sonar.login>admin</sonar.login>
    <sonar.password>admin</sonar.password>
    <sonar.java.coveragePlugin>jacoco</sonar.java.coveragePlugin>
    <sonar.coverage.jacoco.xmlReportPaths>target/site/jacoco/jacoco.xml</sonar.coverage.jacoco.xmlReportPaths>
</properties>
```

### Alterando as configurações

- **sonar.host.url**: URL do servidor SonarQube (altere se usar um servidor remoto ou SonarCloud)
- **sonar.login** e **sonar.password**: Credenciais de autenticação
- **sonar.projectKey**: Identificador único do projeto no SonarQube
- **sonar.organization**: Organização (necessário para SonarCloud)

### Usando SonarCloud

Para usar o SonarCloud em vez de um servidor local:

1. Crie uma conta em https://sonarcloud.io
2. Crie um novo projeto e obtenha o token de autenticação
3. Altere as propriedades no `pom.xml`:

```xml
<sonar.host.url>https://sonarcloud.io</sonar.host.url>
<sonar.organization>sua-organizacao</sonar.organization>
<sonar.projectKey>sua-organizacao_PousadaBackend</sonar.projectKey>
<sonar.login>seu-token-aqui</sonar.login>
<!-- Remova sonar.password ao usar token -->
```

## Usando variáveis de ambiente (recomendado)

Para não expor credenciais no código, use variáveis de ambiente:

```bash
export SONAR_HOST_URL=http://localhost:9000
export SONAR_LOGIN=admin
export SONAR_PASSWORD=admin

mvn clean verify sonar:sonar
```

No Windows (PowerShell):

```powershell
$env:SONAR_HOST_URL="http://localhost:9000"
$env:SONAR_LOGIN="admin"
$env:SONAR_PASSWORD="admin"

mvn clean verify sonar:sonar
```

## Usando ngrok para acesso externo

Se você precisa acessar o SonarQube de fora da sua máquina local, use o ngrok para expor o servidor.

### 1. Instalar o ngrok

1. Baixe o ngrok em https://ngrok.com/download
2. Extraia o arquivo baixado
3. Adicione o diretório do ngrok ao PATH do Windows ou execute diretamente

### 2. Iniciar o SonarQube com Docker

```bash
docker run -d --name sonarqube -p 9000:9000 sonarqube:lts
```

### 3. Expor o SonarQube com ngrok

```bash
ngrok http 9000
```

O ngrok irá gerar uma URL pública (ex: `https://xxxx-xx-xx-xx-xx.ngrok-free.app`) que você pode usar para acessar o SonarQube de qualquer lugar.

### 4. Atualizar configuração do Maven (opcional)

Se quiser enviar análises usando a URL do ngrok, atualize o `sonar.host.url` no `pom.xml` ou use variável de ambiente:

```powershell
$env:SONAR_HOST_URL="https://sua-url-ngrok.ngrok-free.app"
mvn clean verify sonar:sonar
```

## Parar o servidor SonarQube

```bash
docker stop sonarqube
docker rm sonarqube
```

## Parar o ngrok

Pressione `Ctrl+C` no terminal onde o ngrok está rodando.

## Integração com CI/CD

Para integrar com GitHub Actions, GitLab CI, ou outras ferramentas de CI/CD, consulte a documentação do SonarQube:
- https://docs.sonarqube.org/latest/analysis/scan/sonarscanner-for-maven/
