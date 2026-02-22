const todo = document.querySelector('#todo');
const progress = document.querySelector('#progress');
const done = document.querySelector('#done');
let draggedElement = null;


const tasks = document.querySelectorAll('.task');

tasks.forEach(task => {
  task.addEventListener('drag', (e) => {
    console.log("dragging", e);
    draggedElement = task;

  })
})


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
    console.log("Dropped", draggedElement, column);

    column.appendChild(draggedElement)
    column.classList.remove("hover-over");
  })
}

addDragEvenetsOnColumn(todo);
addDragEvenetsOnColumn(progress);
addDragEvenetsOnColumn(done);
