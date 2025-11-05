<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=false displayMessage=!messagesPerField.existsError('username'); section>
    <#if section = "header">
        <#-- Header vazio para não mostrar título padrão -->
    <#elseif section = "form">
    <div class="login-page-angular">
      
      <!-- Seção do Formulário (Esquerda) -->
      <div class="form-section-angular">
        
        <!-- Logo -->
        <div class="logo-container">
          <img src="${url.resourcesPath}/img/logo.avif" alt="logo sistema" onerror="this.style.display='none'" />
        </div>

        <!-- Título -->
        <h2 class="login-title-angular">Recupere sua conta</h2>

        <!-- Instrução -->
        <p class="reset-instruction-angular">
          Digite seu e-mail para receber instruções de recuperação
        </p>

        <!-- Formulário -->
        <form id="kc-reset-password-form" class="login-form-angular" action="${url.loginAction}" method="post">
          
          <!-- Campo de Email -->
          <div class="form-group login-field-angular">
            <label for="username" class="login-label-angular">Email</label>
            <input type="text" 
                   id="username" 
                   name="username" 
                   class="form-control login-input-angular" 
                   autofocus 
                   placeholder="example@gmail.com"
                   value="${(auth.attemptedUsername!'')}" 
                   aria-invalid="<#if messagesPerField.existsError('username')>true</#if>"/>
            <#if messagesPerField.existsError('username')>
              <span class="error-message-angular">
                ${kcSanitize(messagesPerField.get('username'))?no_esc}
              </span>
            </#if>
          </div>

          <!-- Botão de Recuperar -->
          <div class="btn-wrapper-angular">
            <button class="btn btn-primary login-button-angular" type="submit">
              Recuperar acesso
            </button>
          </div>

        </form>

        <!-- Link para voltar ao login -->
        <div class="btn-wrapper-angular-secondary">
          <div class="divider">
            <div></div>
            <span>ou</span>
            <div></div>
          </div>

          <a href="${url.loginUrl}" class="btn btn-secondary login-signup-link-angular">
            Voltar para o login
          </a>
        </div>

      </div>

      <!-- Seção Lateral Azul (Direita) -->
      <div class="main-section-angular">
        <div class="illustration">
          <img src="${url.resourcesPath}/img/main-ilustration.avif" alt="Ilustração" onerror="this.style.display='none'" />
        </div>
      </div>

    </div>
    </#if>
</@layout.registrationLayout>