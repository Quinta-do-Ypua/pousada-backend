<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Redefinir Senha</title>
</head>
<body style="margin: 0; padding: 0; font-family: 'Inter', 'Segoe UI', system-ui, -apple-system, sans-serif; background-color: #ffffff;">

<table width="100%" cellpadding="0" cellspacing="0" border="0" style="padding: 40px 20px;">
    <tr>
        <td align="center">

            <!-- Container -->
            <table width="600" cellpadding="0" cellspacing="0" border="0">

                <!-- Logo/Título -->
                <tr>
                    <td style="padding: 0 0 30px 0;">
                        <h1 style="color: rgb(139, 69, 19); margin: 0; font-size: 24px; font-weight: 700;">
                            Quinta do Ypuã
                        </h1>
                    </td>
                </tr>

                <!-- Conteúdo -->
                <tr>
                    <td>

                        <h2 style="color: #555555; font-size: 20px; font-weight: 600; margin: 0 0 20px 0;">
                            Redefinir Senha
                        </h2>

                        <p style="color: #555555; font-size: 16px; line-height: 1.6; margin: 0 0 25px 0;">
                            Você solicitou a redefinição de senha da sua conta. Clique no botão abaixo para continuar:
                        </p>

                        <!-- Botão -->
                        <table cellpadding="0" cellspacing="0" border="0" style="margin: 25px 0;">
                            <tr>
                                <td>
                                    <a href="${link}" style="display: inline-block; background-color: rgba(139, 69, 19, 0.85); color: #ffffff; text-decoration: none; padding: 14px 32px; border-radius: 8px; font-size: 15px; font-weight: 500;">
                                        Redefinir Senha
                                    </a>
                                </td>
                            </tr>
                        </table>

                        <p style="color: #888888; font-size: 14px; line-height: 1.6; margin: 20px 0;">
                            Este link expira em <strong>${linkExpiration} minutos</strong>.
                        </p>

                        <p style="color: #888888; font-size: 14px; line-height: 1.6; margin: 20px 0 0 0;">
                            Se você não fez esta solicitação, ignore este email.
                        </p>

                        <!-- Linha divisória -->
                        <div style="border-top: 1px solid #eeeeee; margin: 30px 0;"></div>

                        <!-- Link alternativo -->
                        <p style="color: #aaaaaa; font-size: 12px; line-height: 1.5; margin: 0;">
                            Ou copie e cole este link: <br/>
                            <span style="color: #8B4513; word-break: break-all;">${link}</span>
                        </p>

                    </td>
                </tr>

                <!-- Footer -->
                <tr>
                    <td style="padding-top: 40px;">
                        <p style="color: #aaaaaa; font-size: 12px; text-align: center; margin: 0;">
                            © ${.now?string('yyyy')} Pousada Quinta do Ypuã
                        </p>
                    </td>
                </tr>

            </table>

        </td>
    </tr>
</table>

</body>
</html>