# Kanban Board — v1 Prototype

A lightweight, browser-based Kanban board built with vanilla HTML, CSS, and JavaScript. No frameworks, no build tools — just open `index.html` and start managing tasks.

![Dark themed Kanban board with three columns]

---

## Features

| Feature | Description |
|---|---|
| **Three Columns** | To Do, In Progress, Done |
| **Add Tasks** | Create tasks with a title and description via a modal dialog |
| **Drag & Drop** | Move tasks between columns using native HTML5 drag-and-drop |
| **Delete Tasks** | Remove tasks with a confirmation prompt |
| **Live Task Counts** | Each column header displays its current task count |
| **Persistence** | All tasks are saved to `localStorage` and restored on reload |

---

## Project Structure

```
kanban/
├── index.html      # Page structure — nav, board columns, modal
├── style.css       # Dark-themed styling with CSS custom properties
├── script.js       # All application logic (DOM, drag-drop, storage)
└── README.md       # This file
```

---

## Architecture

### Overview

The app follows a simple **DOM-driven architecture** — the DOM is the single source of truth during runtime, and state is serialized to `localStorage` on every mutation (add, move, delete).

```
┌──────────────────────────────────────────────────┐
│                   index.html                     │
│  ┌──────────┐  ┌──────────────┐  ┌───────────┐  │
│  │  To Do   │  │ In Progress  │  │   Done     │  │
│  │ (column) │  │  (column)    │  │  (column)  │  │
│  │          │  │              │  │            │  │
│  │  [task]  │  │   [task]     │  │  [task]    │  │
│  │  [task]  │  │              │  │            │  │
│  └──────────┘  └──────────────┘  └───────────┘  │
│                                                  │
│  ┌────────────────────────────────────────────┐  │
│  │  Modal (hidden by default)                 │  │
│  │  - Title input                             │  │
│  │  - Description textarea                    │  │
│  │  - Add Task button                         │  │
│  └────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────┘
         │                      ▲
         ▼                      │
┌──────────────────────────────────────────────────┐
│                   script.js                      │
│                                                  │
│  createTaskElement(title, desc)                  │
│    → builds DOM node with drag + delete handlers │
│                                                  │
│  updateCountsAndSave()                           │
│    → reads DOM → updates counts → writes to      │
│      localStorage                                │
│                                                  │
│  addDragEventsOnColumn(column)                   │
│    → dragenter / dragleave / dragover / drop      │
│                                                  │
│  Modal toggle / background-click-to-close        │
│  Add-task handler (validate → create → save)     │
│  localStorage restore on page load               │
└──────────────────────────────────────────────────┘
         │                      ▲
         ▼                      │
┌──────────────────────────────────────────────────┐
│               localStorage                       │
│                                                  │
│  key: "tasks"                                    │
│  value: {                                        │
│    "todo":     [{ title, description }, ...],    │
│    "progress": [{ title, description }, ...],    │
│    "done":     [{ title, description }, ...]     │
│  }                                               │
└──────────────────────────────────────────────────┘
```

### Data Flow

```
User Action          Function Called             Side Effects
─────────────        ────────────────            ────────────
Add task         →   createTaskElement()     →   DOM append + updateCountsAndSave()
Drag & drop      →   drop event handler     →   DOM move   + updateCountsAndSave()
Delete task      →   delete click handler   →   DOM remove + updateCountsAndSave()
Page load        →   localStorage restore   →   createTaskElement() per saved task
```

### Key Functions

#### `createTaskElement(title, description)`
Factory function that builds a task `<div>` with:
- `draggable="true"` attribute
- Inner HTML (title `<h2>`, description `<p>`, delete `<button>`)
- `drag` event listener (sets `draggedElement` reference)
- `click` listener on the delete button (with `confirm()` prompt)

#### `updateCountsAndSave()`
Called after every state mutation. Iterates through all three columns, counts `.task` elements, updates the column header count, and serializes the full board state to `localStorage`.

#### `addDragEventsOnColumn(column)`
Registers four drag-and-drop event listeners on a column:
- `dragenter` — adds `.hover-over` visual class
- `dragleave` — removes `.hover-over`
- `dragover` — calls `preventDefault()` to allow drops
- `drop` — appends the dragged element, removes hover class, saves state

### Styling

- **Dark theme** using CSS custom properties (`:root` variables)
- Colors: `--bg-color` (black), `--bg-button-color`, `--bg-task-color`, `--delete-color`
- Spacing and radii controlled via `--padding-*` and `--border-radius-*` tokens
- Modal uses `backdrop-filter: blur(5px)` overlay with `z-index` layering
- Smooth column transitions with `cubic-bezier` easing

### localStorage Schema

```json
{
  "todo": [
    { "title": "Task 1", "description": "Description here" }
  ],
  "progress": [
    { "title": "Task 2", "description": "Another description" }
  ],
  "done": []
}
```

---

## Getting Started

1. Clone or download this folder
2. Open `index.html` in any modern browser
3. Click **"Add new Task"** to create your first task
4. Drag tasks between columns to update their status

No server, no dependencies, no build step required.

---

## Browser Support

Works in all modern browsers that support:
- HTML5 Drag and Drop API
- `localStorage`
- CSS Custom Properties
- `backdrop-filter`

---

## Future Improvements (v2)

- Edit existing tasks
- Task priority levels / color labels
- Due dates and reminders
- Search and filter
- Reorder tasks within a column
- Mobile / touch drag support
- Export / import board data
