//DOM요소 연결을 시켜준다.
const screen = document.getElementById("screen");
const buttons = document.querySelectorAll("button");

//연산자 구별정규식
const operatorRegex = /^(\d+|\*\*|[+\-*/])$/;

//숫자구별 정규식
const numberRegex = /[0-9]/g;

// input태그 화면에 숫자or연산자를 추가하는 함수
function appendToScreen(value) {
    screen.value += value;
}

//화면 초기화
function clearScreen() {
    screen.value = "";
}

// 연산 수행 함수
function caculate(operatorRegex, numbers) {
    // numbers에 배열로된 데이터들을 넣을예정(숫자, 연산자)
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
            return num1 / num2;
        default:
            return "";
    }
}

// 버튼 클릭시 동작을 처리하는 함수.
function handleButtonClick(event) {
    event.preventDefault();
    const buttonText = event.target.innerText;

    if(numberRegex.test(buttonText) == true) {
        appendToScreen(buttonText);
    } else if(operatorRegex.test(buttonText) == true) {
        appendToScreen(buttonText);
    }
}

function initializeButtonListeners() {
    buttons.forEach((button) => {
        button.addEventListener("click", handleButtonClick);
    })
}

// "="버튼 클릭시 계산결과를 화면에 표시
function handleResultClick() {
    const screenValue = screen.value;

    if(screenValue.includes("+")) {
        const[num1, num2] = screenValue.split("+");
        screen.value = caculate("+", [num1,num2]);
    } else if(screenValue.includes("-")) {
        const[num1, num2] = screenValue.split("-");
        screen.value = caculate("-", [num1,num2]);
    } else if(screenValue.includes("*")) {
        const[num1,num2] = screenValue.split("*");
        screen.value = caculate("*", [num1, num2]);
    } else if(screenValue.includes("/")) {
        const[num1, num2] = screenValue.split("/");
        screen.value = caculate("/", [num1, num2]);
    }
}


// 초기화버튼 클릭시 화면초기화
document.getElementById("resetButton").addEventListener("click", function() {
    clearScreen();
});

// "="버튼 클릭시 계산실행
document.getElementById("result").addEventListener("click", handleResultClick);

// 계산기 기능 실행
initializeButtonListeners();