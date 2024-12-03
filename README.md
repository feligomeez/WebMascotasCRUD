# WebMascotasCRUD

Este proyecto es una aplicación web destinada a la gestión de mascotas, que incluye funcionalidades como el registro de usuarios, la creación de una cesta de adopción, todo conectado a una base de datos. Además, permite la selección de provincias y localidades mediante peticiones AJAX, e incorpora medidas de seguridad como el uso de CAPTCHA para evitar registros automáticos.

## Tecnologías Utilizadas

### Backend
- **Java EE**: Usado para la lógica de negocio y la implementación de controladores.
- **JPA**: Para la persistencia de datos en la base de datos.
- **MySQL**: Sistema de gestión de bases de datos.
- **GlassFish**: Servidor de aplicaciones.
- **Maven**: Para la gestión de dependencias y configuración del proyecto.

### Frontend
- **HTML**: Para la estructura de las vistas.
- **CSS** (incluido Bootstrap): Para el diseño y estilo.
- **JavaScript**: Incorporado para la interacción del usuario y las peticiones asíncronas con AJAX, además de proporcionar validación en tiempo real de formularios y la generación dinámica del menú de navegación (nav) en función del estado del usuario.

### Seguridad
- **Google reCAPTCHA**: Implementado para prevenir registros automáticos.
- **Validación asíncrona**: Comprobación de datos en tiempo real, como el email de usuario ya registrado.

## Funcionalidades Principales

1. **Registro de Usuarios**:
   - Validación de correo electrónico en tiempo real mediante AJAX.
   - CAPTCHA para evitar registros automáticos.
   - Recuperación de contraseñas con validación adicional basada en atributos del usuario.

2. **Gestión de Mascotas**:
   - Cada usuario puede añadir, editar y gestionar mascotas vinculadas a su perfil.

3. **Implementación de una Cesta**:
   - Sistema que permite a los usuarios añadir mascotas a una cesta para su gestión o adopción.

4. **Selección de Provincias y Localidades**:
   - Implementación de un selector dinámico para provincias y localidades mediante peticiones AJAX.
