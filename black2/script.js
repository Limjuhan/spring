//DOM요소 연결을 시켜주야 한다.
const screen = document.getElementById("screen");
const buttons = document.querySelectorAll("button");
//document.querySelector(); =>태그한개만 가지고와서 변수담는다.
//document.querySelectorAll(); => 태그여러개를 리스트형대로 담아서 변수에담는다.

//연산자 구별정규식
const operatorRegex = /^(\d+|\*\*|[+\-*/])$/;

//숫자구별 정규식
const numberRegex = /[0-9]/g;

//input태그 화면에 숫자 또는 연산자를 추가하는 함수
function appendToScreen(value) {
    screen.value += value;
}

//화면 초기화
function clearScreen() {
    screen.value = ""; // 빈 인풋값
}

//연산 수행 함수
function caculate(operator, numbers) {
    // numbers에 배열로 된 데이터들을 넣을예정(숫자,연산자)
    // numbers.map(Number) => numbers안에있는 배열데이터들을 전부 숫자화
    const[num1, num2] = numbers.map(Number);

    switch(operator) {
        case "+":
            return num1 + num2;
        case "-":
            return num1 - num2;
        case "*":
            return num1 * num2;
        case "/":
            return num2 !== 0?num1/num2:"Error";
        default:
            return "";
    }
}

// 버튼 클릭시 동작을 처리하는 함수.
function handleButtonClick(event) {
    event.preventDefault();// 새로고침 방지.데이터 날라가는거 막기위해
    const buttonText = event.target.innerText;
    
    if(numberRegex.test(buttonText) == true) {
        appendToScreen(buttonText);
    } else if(operatorRegex.test(buttonText) == true) {
        appendToScreen(buttonText);
    }
}

// 버튼클릭 이벤트리스너 등록함수
function initializeButtonListeners() {
    buttons.forEach((button)=>{
        button.addEventListener("click", handleButtonClick);

    })
}

// "="버튼 클릭시 계산 결과를 화면에 표시
function handleResultClick() {
    const screenValue = screen.value;

    if(screenValue.includes("+")) {
        const [num1, num2] = screenValue.split("+");
        screen.value = caculate("+", [num1,num2]);
    } else if(screenValue.includes("-")) {
        const [num1, num2] = screenValue.split("-");
        screen.value = caculate("-", [num1,num2]);
    } else if(screenValue.includes("*")) {
        const [num1, num2] = screenValue.split("*");
        screen.value = caculate("*", [num1,num2]);
    } else if(screenValue.includes("/")) {
        const [num1, num2] = screenValue.split("/");
        screen.value = caculate("/", [num1,num2]);
    }
}

// 초기화버튼클릭시 화면 초기화
document.getElementById("resetButton").addEventListener("click", clearScreen);

// "="버튼 클릭시 계산실행
document.getElementById("result").addEventListener("click",handleResultClick);

//계산기 기능 실행
initializeButtonListeners();