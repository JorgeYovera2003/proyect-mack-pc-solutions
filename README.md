# MACK PC Solutions – Sistema de gestión

Proyecto académico de **Diseño de Patrones**. Aplicación de escritorio en **Java 17** con interfaz **Swing**
y persistencia en archivos **JSON** (sin base de datos). Gestiona ventas, cotizaciones, inventario y
servicios técnicos de un negocio de tecnología.

## Estado del proyecto

| Incremento | Contenido | Estado |
|---|---|---|
| 1 | Base: proyecto Maven, persistencia JSON, usuarios y roles, inicio de sesión, ventana principal | Hecho |
| 2 | Catálogo e inventario (Factory Method, Observer) | Pendiente |
| 3 | Clientes y órdenes de servicio (State, Template Method) | Pendiente |
| 4 | Precios y cotización (Strategy, Decorator, Chain of Responsibility) | Pendiente |
| 5 | Kits y equipos a medida (Composite, Builder, Prototype) | Pendiente |
| 6 | Facade, Command, reportes con Proxy y pulido | Pendiente |

## Requisitos

- **JDK 17 o superior** (Maven y los IDE lo usan para compilar).
- **IntelliJ IDEA** o **NetBeans** (traen Maven integrado). No hace falta instalar nada más.

## Cómo abrir el proyecto

**IntelliJ IDEA:** `File > Open…` → elegir el archivo `pom.xml` → "Open as Project". Esperar a que
descargue las dependencias. Abrir `src/main/java/com/mack/Main.java` y ejecutar `main`.

**NetBeans:** `File > Open Project…` → elegir la carpeta del proyecto (NetBeans la reconoce como proyecto Maven).
Clic derecho sobre el proyecto → `Run`.

## Usuarios de ejemplo (se crean en la primera ejecución para el ejmplo)

| Usuario | Contraseña | Rol |
|---|---|---|
| duena | duena123 | Dueña (acceso completo) |
| tecnico | tecnico123 | Técnico (órdenes y garantías) |
| vendedor | vendedor123 | Vendedor (clientes, catálogo, armado, cotizaciones) |

Los datos se guardan en la carpeta `data/`. Cada archivo mantiene una copia
de respaldo `.bak` del guardado anterior.

## Estructura

```
src/main/java/com/mack/
├── Main.java, Aplicacion.java   arranque y ensamblado de las piezas
├── vista/                        ventanas Swing (sin reglas de negocio)
├── controlador/                  conecta vistas con servicios (MVC)
├── servicio/                     casos de uso (iniciar sesión, ...)
├── dominio/                      entidades y contratos (Repositorio)
├── persistencia/                 RepositorioJson (Gson) con respaldo
└── seguridad/                    Usuario, Rol, Modulo, Claves
src/test/java/com/mack/           pruebas unitarias (JUnit 5)
```

## Decisiones de diseño

- **Repository:** el dominio solo conoce la interfaz `Repositorio`; `RepositorioJson` es un detalle intercambiable.
- **Sin Singleton:** las dependencias se entregan en `Aplicacion` (para que probar sea mas facil).
- **MVC:** la vista no tiene reglas de negocio; el controlador coordina vista y servicio.
- **Contraseñas:** se guarda un resumen SHA-256 con sal, nunca la contraseña en claro.
