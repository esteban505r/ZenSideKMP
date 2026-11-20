# ZenSide — Product Spec

> Draft for the design pass. See the published artifact for the formatted version.

## Vision & audience

ZenSide turns a few minutes of stillness into something a child will actually choose — a posture
named "Rana Saltarina" instead of "exercise 5", a story instead of a worksheet. Three people share
the screen: the **child** doing the posture, and the **teacher or parent** who picked the app and
needs it to require no explanation.

Content is Spanish-first. Sessions are short by design — most posturas run under a minute —
because the unit of attention is a classroom transition or a bedtime wind-down, not a workout.

## What's built — v1 (local-only, no backend yet)

| Feature | Status |
|---|---|
| Cuenta local (register/login, on-device) | ✅ live |
| Catálogo de posturas (12 posturas, 4 categorías) | ✅ live |
| Detalle de postura | ✅ live |
| Rutinas (sample sequences grouped by categoría) | ✅ live |

Platforms: Android + Desktop verified to build and run; iOS source compiles but is untested (no
Xcode in the current dev environment).

## Roadmap

### Phase 2 — make a session worth returning to (no backend required)
- **Cuentacuentos**: short illustrated stories as a second content type. Title, cover, paginated
  text, optional read-aloud audio.
- **Rutina player**: step-through mode, one postura at a time, visible per-step timer,
  auto-advance, progress dots.
- **Respiración guiada**: standalone breathing exercise, animated expand/contract shape timed to
  inhale/exhale.
- **Perfil**: display name, avatar, a log of posturas/rutinas completed this week.

### Phase 3 — connect the real backend (blocked on the backend team's API contract)
- **Auth real**: networked login/register, token refresh, real password handling, password reset.
- **Contenido remoto**: posturas/rutinas/stories served from the backend instead of bundled JSON.
- **Sincronización**: progress and profile sync across devices.

The domain layer already has the seam for this — swap `LocalAuthRepository` /
`LocalPosturaRepository` for networked implementations with zero UI changes.

### Phase 4 — serve the adult in the room (needs accounts to distinguish roles)
- **Panel docente**: which rutinas a group completed this week, suggested next session.
- **Perfiles múltiples**: one adult account, several child profiles.
- **Información / recursos**: reference content explaining each categoría, written for adults.
- **Insignias**: light, non-competitive recognition for consistency.

## Data entities

| Entity | Key fields | Shown on |
|---|---|---|
| `User` | displayName, email, avatar (planned) | Login, Register, Perfil |
| `Postura` | nombre, categoría, tiempo, descripción, recomendaciones[] | Catálogo, Detalle, Rutina player |
| `Rutina` | nombre, categoría, posturas[] | Rutinas, Rutina player |
| `Historia` (planned) | título, portada, páginas[], audio | Cuentacuentos |
| `Progreso` (planned) | completadoEn, tipo, racha | Perfil, Panel docente |

## Screen inventory

Login · Register · Posturas (list) · Postura (detail) · Rutinas (list) — **live**
Rutina player · Cuentacuentos (list) · Historia (reader) · Respiración guiada · Perfil — **phase 2**
Panel docente · Información — **phase 4**

## Core user flows

**Onboarding → first postura**: Open app → Regístrate → Posturas (catálogo) → elegir postura →
full instructions + recomendaciones.

**Teacher runs a rutina** (phase 2 target): Rutinas (browse by categoría) → iniciar rutina →
auto-advance through posturas with a timer → fin de rutina, logged to Perfil.

## Design notes

The shipped app already carries a starting visual direction: lavender (`#6C4CDB`) primary against
a near-white violet-tinted background, mint (`#3FB68A`) secondary, generous rounding (12–24px),
centered card-based forms. Treat it as a draft, not a constraint.

v1 is the safe surface to design against now. Phase 2 has real data shapes defined above even
though no screen exists yet. Phases 3–4 depend on the backend team's (not yet finalized) API
contract — treat their screens as directional, not ready to hand off.
