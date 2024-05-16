const submitBtn = document.getElementById("btn-form");
const submitText = document.getElementById("text-form");

const dateP = document.getElementById("p-date");
const dateI = document.getElementById("ipt-date");

const modalBtn = document.getElementById("btn-modal");
let modalModText = document.getElementById("text-modify");
const modalModBtn = document.getElementById("btn-modify");
const modalCloseBtn = document.getElementById("btn-closeModal");
const modalSpanId = document.getElementById("span-modId");
// input type=date 를 오늘 날짜로 초기화

const slist = document.getElementById("schedule");

const incomp = document.getElementById("incomp");

window.onload = function () {
  incomp.innerText = 0;

	// 현재 날짜 불러오는건 spring 에서 넘겨준 model("date", date); 를 현재 날짜로 지정
  // dateI.value = new Date().toISOString().substring(0, 10);
  dateP.innerText = dateI.value;
  getSchedules();
};

dateI.addEventListener("change", () => {
  // schedule list 비우기
  while (slist.firstChild) slist.removeChild(slist.firstChild);

  getSchedules();
  countSchedules();
});

async function getSchedules() {
  let year = dateI.value.substr(0, 4);
  let month = dateI.value.substr(5, 2);
  let day = dateI.value.substr(8, 2);

  let response = await fetch(
    API_CALENDAR + "/" + year + "/" + month + "/" + day,
    {
      method: "GET",
      cache: "no-cache", // cache, headers 생략 해도 될듯
      headers: {
        "Content-Type": "application/json",
      },
    }
  );
  let schedules = await response.json();

  schedules.map((data) => createSchedule(data.id, data.content, data.completed));
}

async function postSchedule(textContent) {
  let response = await fetch(API_CALENDAR, {
    method: "POST",
    headers: {
      "Content-Type": "application/json; charset=utf-8",
    },
    body: JSON.stringify({
      content: textContent,
      date: dateI.value,
      isCompleted: "false",
    }),
  });

  let result = await response.json();

  createSchedule(result.id, submitText.value, false);
}

async function putSchedule(id, textContent, isCompleted) {
  let response = await fetch(
    API_CALENDAR + "/" + id.substring(ID_SCHEDULE.length),
    {
      method: "PUT",
      headers: {
        "Content-Type": "application/json; charset-utf-8",
      },
      body: JSON.stringify({
        content: textContent,
        completed: isCompleted,
      }),
    }
  );
}

async function deleteSchedule(id) {
  let response = await fetch(
    API_CALENDAR + "/" + id.substring(ID_SCHEDULE.length),
    {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json; charset-utf-8",
      },
    }
  );
}

submitText.addEventListener("keydown", (event) => {
  if (event.key == "Enter" && event.shiftKey == false) {
    event.preventDefault();
    if (event.target.value === "") return;

    postSchedule(submitText.value);
  }
});

submitBtn.addEventListener("click", (event) => {
  event.preventDefault();
  if (submitText.value === "") return;

  postSchedule(submitText.value);
});

modalModText.addEventListener("keydown", (event) => {
  if (event.key == "Enter" && event.shiftKey == false) {
    event.preventDefault();
    if (event.target.value === "") return;
    modalModBtn.click();
  }
});

modalModBtn.addEventListener("click", () => {
  const article = document.getElementById(modalSpanId.value);
  const textarea = article.children[0].children[0];
  putSchedule(modalSpanId.value, modalModText.value, isCompleted(article));

  textarea.value = modalModText.value;
  modalCloseBtn.click();
});

function handleTextarea(event) {
  if (event.key === "Enter") {
  }
}

function createSchedule(id, content, completed) {
  // create elements
  const article = document.createElement("article");
  const fdiv = document.createElement("div");
  const textarea = document.createElement("textarea");
  const bgGroup = document.createElement("div");
  const btnMod = document.createElement("button");
  const btnComp = document.createElement("button");

  const sdiv = document.createElement("div");
  const btnDel = document.createElement("button");

  // todo: Up, Down 버튼 일단 나중에 구현하자
  // const btnUp = document.createElement("button");
  // const btnDown = document.createElement("button");

  article.className = "list-group-item d-flex py-1";
  if (completed) {
    article.classList.add(FINISHED);
  }

  //create fdiv
  fdiv.className = "col-11";
  textarea.readOnly = true;
  textarea.rows = 3;
  textarea.className = "article-text";
  textarea.textContent = content;

  bgGroup.className =
    "bg-group d-flex gap-1 justify-content-start align-items-center";
  btnComp.className =
    "btn btn-sm btn-outline-primary rounded-pill ms-auto me-1 ";
  btnComp.innerText = "완료";
  btnMod.className = "btn btn-sm btn-outline-secondary rounded-pill";
  btnMod.innerText = "수정";
  btnComp.addEventListener("click", handleCompBtn);
  btnMod.addEventListener("click", handleModal);

  // if (badge 가 있으면 bgGroup 에 넣기)
  bgGroup.appendChild(btnComp);
  bgGroup.appendChild(btnMod);
  fdiv.appendChild(textarea);
  fdiv.appendChild(bgGroup);

  //create sdiv
  // btnUp.className = "btn btn-sm btn-outline-white";
  // btnUp.innerText = UP;
  // btnDown.className = "btn btn-sm btn-outline-white";
  // btnDown.innerText = DOWN;
  btnDel.className = "btn btn-sm btn-outline-white";
  btnDel.innerText = DEL;
  btnDel.addEventListener("click", handleDelete);

  sdiv.className = "d-flex flex-column justify-content-center";
  // sdiv.appendChild(btnUp);
  sdiv.appendChild(btnDel);
  // sdiv.appendChild(btnDown);

  article.appendChild(fdiv);
  article.appendChild(sdiv);
  article.id = ID_SCHEDULE + id;

  slist.append(article);

  submitText.value = "";
  countSchedules();
}

// 현재 날짜 제어
dateI.addEventListener("change", (event) => {
  // 명시적 날짜 지정 = date.value = "2020-12-30";
  dateP.innerText = event.target.value;
});

function handleCompBtn(event) {
  let target = event.target;
  // 부모요소 얻어냄. article
  while (target.tagName.toLowerCase() !== "article") {
    target = target.parentNode;
  }

  let completed = false;
  if (isCompleted(target)) {
    target.classList.remove(FINISHED);
    completed = false;
  } else {
    target.classList.add(FINISHED);
    completed = true;
  }
  const content = target.children[0].children[0].value;
  putSchedule(target.id, content, completed);
  countSchedules();
}

function isCompleted(target) {
  return target.classList.contains(FINISHED);
}

function handleDelete(event) {
  const article = event.target.parentNode.parentNode;

  deleteSchedule(article.id);

  article.remove();
  countSchedules();
}

function handleModal(event) {
  // textarea = 수정하려는 텍스트
  const article = event.target.parentNode.parentNode.parentNode;
  const textarea = article.children[0].children[0];
  modalSpanId.value = article.id;

  modalBtn.click();
  modalModText.value = textarea.value;
  modalModText.focus();
}

/**
 * description: 남은 일정 개수를 구하는 함수. 완료된 일정은 더하지 않는다.
 */
function countSchedules() {
  let count = 0;

  for (const child of slist.children) {
    if (!child.classList.contains(FINISHED)) count++;
  }

  incomp.innerText = count;
}
