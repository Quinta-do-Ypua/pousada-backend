<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=false; section>
    <#if section = "header">
        <#-- Header vazio para não mostrar título padrão -->
    <#elseif section = "form">
    <div class="login-page-angular">
      
      <!-- Seção do Formulário (Esquerda) -->
      <div class="form-section-angular">
        
        <!-- Logo -->
        <div class="logo-container">
          <img src="${url.resourcesPath}/img/logo.avif" alt="Logo" onerror="this.style.display='none'" />
        </div>

        <!-- Título -->
        <h2 class="login-title-angular">Redefinir sua senha</h2>

        <!-- Instrução -->
        <p class="reset-instruction-angular">
          Digite sua nova senha abaixo
        </p>

        <!-- Formulário -->
        <form id="kc-passwd-update-form" class="login-form-angular" action="${url.loginAction}" method="post">
          
          <!-- Nova Senha -->
          <div class="form-group login-field-angular">
            <label for="password-new" class="login-label-angular">Nova senha</label>
            <input type="password" 
                   id="password-new" 
                   name="password-new" 
                   class="form-control login-input-angular" 
                   autofocus 
                   autocomplete="new-password"
                   placeholder="Digite sua nova senha"
                   aria-invalid="<#if messagesPerField.existsError('password','password-confirm')>true</#if>" />
            <#if messagesPerField.existsError('password')>
              <span class="error-message-angular">
                ${kcSanitize(messagesPerField.get('password'))?no_esc}
              </span>
            </#if>
          </div>

          <!-- Confirmar Senha -->
          <div class="form-group login-field-angular">
            <label for="password-confirm" class="login-label-angular">Confirmar senha</label>
            <input type="password" 
                   id="password-confirm" 
                   name="password-confirm" 
                   class="form-control login-input-angular"
                   autocomplete="new-password"
                   placeholder="Digite novamente sua senha"
                   aria-invalid="<#if messagesPerField.existsError('password-confirm')>true</#if>" />
            <#if messagesPerField.existsError('password-confirm')>
              <span class="error-message-angular">
                ${kcSanitize(messagesPerField.get('password-confirm'))?no_esc}
              </span>
            </#if>
          </div>

          <!-- Botão de Atualizar -->
          <div class="btn-wrapper-angular">
            <input type="hidden" id="id-hidden-input" name="credentialId" <#if auth.selectedCredential?has_content>value="${auth.selectedCredential}"</#if>/>
            <button class="btn btn-primary login-button-angular" type="submit">
              Atualizar senha
            </button>
          </div>

        </form>

        <!-- Link para voltar ao login -->
        <#if isAppInitiatedAction??>
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
        </#if>

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
