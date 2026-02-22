let tasksData = {}
const todo = document.querySelector('#todo');
const progress = document.querySelector('#progress');
const done = document.querySelector('#done');
const columns = [todo, progress, done]
let draggedElement = null;

function createTaskElement(title, description) {
  const div = document.createElement('div');
  div.classList.add("task");
  div.setAttribute("draggable", "true");
  div.innerHTML = `
    <h2>${title}</h2>
    <p>${description}</p>
    <button>Delete</button>
  `;
  div.addEventListener('drag', (e) => {
    draggedElement = div;
  });
  div.querySelector('button').addEventListener('click', () => {
    if (confirm(`Delete task "${title}"?`)) {
      div.remove();
      updateCountsAndSave();
    }
  });
  return div;
}

function updateCountsAndSave() {
  columns.forEach(col => {
    const tasks = col.querySelectorAll('.task');
    const count = col.querySelector('.right');
    count.innerText = tasks.length;
    tasksData[col.id] = Array.from(tasks).map(t => {
      return {
        title: t.querySelector('h2').innerText,
        description: t.querySelector('p').innerText
      };
    });
  });
  localStorage.setItem("tasks", JSON.stringify(tasksData));
}

if (localStorage.getItem("tasks")) {
  const data = JSON.parse(localStorage.getItem("tasks"));
  for (const col in data) {
    const column = document.querySelector(`#${col}`);
    if (column) {
      data[col].forEach(task => {
        const div = createTaskElement(task.title, task.description);
        column.appendChild(div);
      });
    }
  }
}

updateCountsAndSave();

const tasks = document.querySelectorAll('.task');

tasks.forEach(task => {
  task.addEventListener('drag', (e) => {
    draggedElement = task;
  });
  const deleteBtn = task.querySelector('button');
  if (deleteBtn) {
    deleteBtn.addEventListener('click', () => {
      const title = task.querySelector('h2')?.innerText || 'this task';
      if (confirm(`Delete task "${title}"?`)) {
        task.remove();
        updateCountsAndSave();
      }
    });
  }
});


function addDragEvenetsOnColumn(column) {
  column.addEventListener('dragenter', (e) => {
    e.preventDefault();
    column.classList.add("hover-over");
  })

  column.addEventListener('dragleave', (e) => {
    e.preventDefault();
    column.classList.remove("hover-over");
  })

  column.addEventListener('dragover', (e) => {
    e.preventDefault();
  })

  column.addEventListener("drop", (e) => {
    e.preventDefault();
    // console.log("Dropped", e);
    // console.log("Dropped", draggedElement, column);

    column.appendChild(draggedElement)
    column.classList.remove("hover-over");

    updateCountsAndSave();
  })
}

addDragEvenetsOnColumn(todo);
addDragEvenetsOnColumn(progress);
addDragEvenetsOnColumn(done);


const toggleModalBtn = document.querySelector('#toggle-modal')
const modalBg = document.querySelector('.modal .bg');
const modal = document.querySelector('.modal');
const addTaskBtn = document.querySelector('#add-new-task');

toggleModalBtn.addEventListener('click', () => {
  modal.classList.toggle('active')
})

modalBg.addEventListener("click", () => {
  modal.classList.remove("active")
})

addTaskBtn.addEventListener('click', () => {
  const taskTitle = document.querySelector("#task-title-input").value;
  const taskDescription = document.querySelector("#task-description-input").value;
  if (!taskTitle.trim()) return;
  const div = createTaskElement(taskTitle, taskDescription);
  todo.appendChild(div);

  updateCountsAndSave();

  document.querySelector("#task-title-input").value = "";
  document.querySelector("#task-description-input").value = "";
  modal.classList.remove("active");
})
