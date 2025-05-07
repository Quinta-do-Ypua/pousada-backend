# URL da API
$API_URL = "http://localhost:8081"

# Função para fazer requisição POST e extrair ID
function Post-Request {
    param (
        [string]$url,
        [string]$body
    )

    $response = Invoke-RestMethod -Uri $url -Method Post -ContentType "application/json" -Body $body
    return $response
}

# Criar Endereço
Write-Host "Criando endereço..."
$enderecoBody = '{
    "cidade": "São Paulo",
    "estado": "SP",
    "cep": "01001-000",
    "bairro": "Centro",
    "numero": "123",
    "complemento": "Apto 45"
}'
$enderecoResponse = Post-Request -url "$API_URL/enderecos" -body $enderecoBody
$enderecoID = $enderecoResponse.id
Write-Host "Endereço criado com ID: $enderecoID"

# Criar Cliente
Write-Host "Criando cliente..."
$clienteBody = @"
{
    "nome": "João Silva",
    "cpf": "12345678900",
    "email": "joao@email.com",
    "senha": "senha123",
    "celular": "11999999999",
    "sexo": "MASCULINO",
    "endereco": { "id": $enderecoID }
}
"@
$clienteResponse = Post-Request -url "$API_URL/clientes" -body $clienteBody
$clienteID = $clienteResponse.id
Write-Host "Cliente criado com ID: $clienteID"

# Criar Quarto
Write-Host "Criando quarto..."
$quartoBody = '{
    "numero": "101",
    "capacidade": 2,
    "valorDiaria": 150.00
}'
$quartoResponse = Post-Request -url "$API_URL/quartos" -body $quartoBody
$quartoID = $quartoResponse.id
Write-Host "Quarto criado com ID: $quartoID"

# Criar Reserva
# Criar Reserva
Write-Host "Criando reserva..."

# Verifique o corpo da requisição para saber se todos os dados estão corretos
$reservaBody = @"
{
    "quarto": { "id": $quartoID },
    "valorDoQuarto": 150.00,
    "valorComplementos": 30.00,
    "status": "ABERTA",
    "observacao": "Reserva para 2 pessoas",
    "checkIn": "2025-06-01T14:00:00",
    "checkOut": "2025-06-05T11:00:00",
    "cliente": { "id": $clienteID }
}
"@
Write-Host "Corpo da requisição para reserva:"
Write-Host $reservaBody

# Enviar a requisição POST
try {
    $reservaResponse = Post-Request -url "$API_URL/reservas" -body $reservaBody
    Write-Host "Reserva criada com sucesso."
} catch {
    Write-Host "Erro ao criar reserva: $_"
}

Write-Host "Reserva criada com sucesso."
