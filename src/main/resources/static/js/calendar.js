// 전체 달력 크기
const XLEN = 6;
const YLEN = 7;

const tagToday = document.getElementById("today");
const btnPre = document.getElementById("btn-pre");
const btnNext = document.getElementById("btn-next");
const btnToday = document.getElementById("btn-today");

btnPre.addEventListener("click", handleMoveBtn);
btnNext.addEventListener("click", handleMoveBtn);
btnToday.addEventListener("click", handleTodayBtn);

const date = new Date();
let year = date.getFullYear();
let month = date.getMonth();
const today = [year, month, date.getDate()];
let todayCoord = [0, 0];

// tr= 달력 한주에 해당
const trArr = new Array(XLEN);
for (let i = 0; i < XLEN; i++) {
  trArr[i] = document.getElementById("tr" + i);
}
let calendarArr = Array.from(Array(XLEN), () => new Array(YLEN));



window.onload = function() {
	// 일정 Arr 가져옴
	render(year, month);
}

/*
구조 예시

<tr id="1">
    <td id="1a">
        <a href="#" class="day">
            <p class="date">1</p>
            <p class="schedule">오늘 한 일 1</p>
            <p class="schedule">한글로 작성</p>
            <p class="schedule-plus">5 more</p>
        </a>
    </td>
</tr>
*/


async function render(year, month) {
  const firstDay = new Date(year, month).getDay();
  const lastDay = new Date(year, month, 0).getDate();
  // 그 달의 일정목록 가져옴
	let response = await fetch(
		`${API_CALENDAR}/${year}/${month+1}`,
		{
			method: "GET",
			cache: "no-cache",
			headers: {
				"Content-Type": "application/json",
			},
		}
	);
	let scheduleList = await response.json();
	let scheduleArr = await addScheduleArr(lastDay, scheduleList);

	await paintCalendar(year, month, firstDay, lastDay, scheduleArr);
}

function addScheduleArr(length, list) {
	let arr = Array.from(Array(length), () => new Array());

		for (let i=0; i<list.length; i++) {
  		// 요일만 추출
  		let sDay = list[i].date.substr(8);
  		arr[sDay - 1].push(list[i].content);
  	}

	return arr;
}


async function paintCalendar(year, month, firstDay, lastDay, scheduleArr) {
  clearCal();

  // new Date() 에서 month 는 1 작게 나옴. 그래서 1 더함
  if (month < 9) tagToday.textContent = year + "  0" + (month + 1);
  else tagToday.textContent = year + " " + (month + 1);

  let count = 1 - firstDay;

  // 나머지주 요소 생성
  for (let i = 0; i < XLEN; i++) {
    for (let j = 0; j < YLEN; j++) {
      let td = document.createElement("td");
      td.id = "td" + i + j;

      if (count <= lastDay && count > 0) {
        let a = document.createElement("a");
        a.className = "day";

				a.href = `\schedule?year=${year}&month=${month + 1}&day=${count}`;
        let p = document.createElement("p");
        p.className = "date";
        p.innerText = count;
        a.appendChild(p);

				// 각 날짜에 일정 추가
        if (scheduleArr[count - 1] !== null) {
          for (let i = 0; i < scheduleArr[count - 1].length; i++) {
						if (i > 1) {
							let goal = document.createElement("p");
							goal.className = "schedule-plus";
							goal.innerText = `+${scheduleArr[count - 1].length - 2}개의 일정`;
							a.appendChild(goal);
							break;
						}
            let goal = document.createElement("p");
            goal.className = "schedule";
            // 길이 8 만큼 목표문자열 자름
						let itext = scheduleArr[count-1][i];
            if (itext.length > 7) {
              itext = itext.substr(0, 7) + "...";
            }
            goal.innerText = itext;
            a.appendChild(goal);
          }
        }


        td.appendChild(a);
      }

      trArr[i].appendChild(td);
      count++;
      calendarArr[i][j] = td;
    }
  }

  if (year === today[0] && month === today[1]) {
    paintToday(firstDay);
  }
}

function handleMoveBtn(event) {
  if (event.target.id == "btn-pre") {
    if (month == 0) {
      year--;
      month = 11;
    } else {
      month--;
    }
    render(year, month);
  } else if (event.target.id == "btn-next") {
    if (month == 11) {
      year++;
      month = 0;
    } else {
      month++;
    }
    render(year, month);
  }
}

function clearCal() {
  for (let i = 0; i < XLEN; i++) {
    trArr[i].replaceChildren();
  }
}

function handleTodayBtn(event) {
  year = today[0];
  month = today[1];
  render(year, month);
}

// 오늘 날짜는 색칠
function paintToday(firstDay) {
  let paintCoord;
  let count = 1 - firstDay;
  let flag = false;

  for (let i = 0; i < XLEN; i++) {
    for (let j = 0; j < YLEN; j++) {
      if (today[2] == count) {
        paintCoord = calendarArr[i][j];
        flag = true;
        break;
      }
      count++;
    }

    if (flag) break;
  }

  paintCoord.className = "border border-5 border-danger";
}
