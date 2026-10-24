> **⚠️ Aviso:** Este proyecto continúa en [VortrexEvents](https://github.com/TenochmcYT/VortrexEvents). Su release 1.0.0 original sigue disponible para descarga en este repositorio.

---

# 🎮 TenochMC Trivia & Castigos

![Versión de Minecraft](https://img.shields.io/badge/Minecraft-1.21.11-brightgreen)
![Paper / Spigot](https://img.shields.io/badge/Platform-Paper%20%2F%20Spigot-orange.svg)
![Estado](https://img.shields.io/badge/Status-Stable%201.0.0-blue)

¡Un plugin definitivo y altamente configurable para servidores de Minecraft (Paper 1.21.11) diseñado para poner a prueba el conocimiento de tus jugadores con eventos automáticos de trivia, sistemas de castigos dinámicos y persistencia de datos!

---

## ✨ Características Principales

- 🤖 **Sistema de Eventos Automáticos:** Configura intervalos de tiempo para lanzar preguntas de trivia automáticas en tu servidor sin intervención constante de los administradores.
- ⚡ **Sistema de Castigos Dinámicos:** ¿No responden a tiempo o fallan? El plugin selecciona aleatoriamente entre más de 40 castigos habilitados en la configuración:
  * 🧪 *Efectos de poción* (Veneno, Wither, Ceguera, Náuseas, etc.).
  * ⚔️ *Daño* (Quitar vida, quemaduras, hambre extrema).
  * 🎒 *Inventario* (Soltar objetos de la mano, vaciar slots al azar, perder experiencia).
  * 🧟 *Invocación de Mobs hostiles y pasivos* (Con un sistema integrado que evita que los jugadores abusen haciendo "farming" de drops).
- 💾 **Persistencia Robusta por Jugador:** Guarda de forma segura el estado de cada jugador (`congelado`, `preguntaIndex`) en archivos YAML individuales por UUID dentro de la carpeta `players/`, asegurando que no pierdan su estado al desconectarse o al reiniciar el servidor.
- 🛠️ **Comandos y Permisos Flexibles:** Control total mediante subcomandos amigables (`/eventos activar`, `desactivar`, `forzar`, `reload`).

---

## 📋 Comandos y Permisos

| Comando | Descripción | Permiso Requerido |
| :--- | :--- | :--- |
| `/eventos activar` | Activa el ciclo de trivias automáticas. | `eventos.comandos.activar` |
| `/eventos desactivar` | Detiene el sistema de trivias automáticas. | `eventos.comandos.desactivar` |
| `/eventos forzar` | Fuerza el inicio inmediato de una pregunta. | `eventos.comandos.forzar` |
| `/eventos reload` | Recarga la configuración del plugin. | `eventos.comandos.reload` |

---

## ⚙️ Configuración (`config.yml`)

El plugin cuenta con una estructura limpia donde puedes habilitar o deshabilitar individualmente cada castigo mediante secciones dinámicas, ajustar el tiempo límite de respuesta y configurar los mensajes a tu gusto.

> **¡Listo para usar!** El plugin ya incluye varias preguntas y respuestas precargadas por defecto, por lo que puedes encender el servidor y empezar a jugar de inmediato sin necesidad de configurar nada desde cero.

<img width="723" height="731" alt="Configuración de Castigos" src="https://github.com/user-attachments/assets/1417331a-6e16-4d7f-8db7-41dab48e0b3a" />

---

## 📥 Instalación

1. Descarga el archivo `.jar` desde la sección de [Descargar Eventos.jar](https://github.com/TenochmcYT/TriviaPlugin/releases/download/Plugins/Eventos.jar).
2. Colócalo en la carpeta `plugins/` de tu servidor Paper/Spigot **1.21.11**.
3. Enciende o reinicia el servidor para generar los archivos de configuración por defecto.
4. ¡Personaliza tu `config.yml` y disfruta del evento!

---
*Desarrollado con ❤️ para la comunidad de Minecraft.*
