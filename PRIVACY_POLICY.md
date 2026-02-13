# Política de Privacidad - SecurePass

**Fecha de entrada en vigor:** 12 de febrero de 2026

En **SecurePass**, la privacidad de tus datos es nuestra prioridad absoluta. Esta política de privacidad detalla cómo nuestra aplicación móvil maneja la información de los usuarios.

## 1. Recopilación y Uso de Información
**SecurePass es una aplicación de arquitectura "Offline-First".** Esto significa que:
* **No recopilamos datos personales:** No solicitamos nombres, correos electrónicos, números de teléfono ni ninguna otra información de identificación personal.
* **Almacenamiento Local:** Todas las contraseñas y datos que ingresas se almacenan exclusivamente en la memoria interna de tu dispositivo.
* **Sin Transmisión de Datos:** La aplicación no cuenta con permisos de red (`INTERNET`), lo que garantiza técnicamente que ninguna información sea enviada a servidores externos o terceros.

## 2. Gestión de Datos Sensibles y Criptografía
Para proteger tu información, implementamos los estándares de seguridad más rigurosos de Android:
* **Cifrado AES-256-GCM:** Tus datos se cifran utilizando algoritmos de grado bancario antes de ser guardados en la base de datos local.
* **Android Keystore System:** Las llaves criptográficas se generan y custodian dentro del hardware de seguridad del dispositivo (TEE/StrongBox), fuera del alcance del sistema operativo y de posibles atacantes.

## 3. Autenticación Biométrica
SecurePass utiliza la API `BiometricPrompt` de Android para permitirte acceder a tus datos:
* La aplicación **no tiene acceso** a tus huellas dactilares o rasgos faciales.
* El proceso de autenticación es gestionado íntegramente por el sistema operativo Android. La app solo recibe una confirmación de éxito o fallo para desbloquear el acceso local.

## 4. Servicios de Terceros
* **Sin Publicidad:** No mostramos anuncios ni utilizamos redes publicitarias.
* **Sin Análisis:** No utilizamos herramientas de rastreo ni análisis (como Firebase Analytics o Google Analytics).
* **Sin SDKs Externos:** No compartimos información con ninguna empresa o entidad externa.

## 5. Permisos Solicitados
La aplicación solo solicita los permisos estrictamente necesarios para su funcionamiento:
* **BIOMETRIC / USE_FINGERPRINT:** Para la autenticación segura del usuario.

## 6. Cambios en esta Política
Esta política de privacidad puede ser actualizada en el futuro para reflejar mejoras en la seguridad de la aplicación. Cualquier cambio será efectivo inmediatamente después de su publicación en este documento.

## 7. Contacto
Si tienes preguntas sobre la privacidad de SecurePass o sobre cómo se protegen tus datos, puedes contactarme directamente:

* **Desarrollador:** Jonathan Sánchez
* **Sitio Web/Portfolio:** [jonathansc.dev](https://jonathansc.dev)
* **Correo:** [hola@jonathansc.dev](mailto:hola@jonathansc.dev)
* **Código Fuente:** [GitHub Repository](https://github.com/jonathanscdev/SecurePass)