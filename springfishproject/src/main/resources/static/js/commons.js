function openModal() {
    let gray_out = document.getElementById("fadeLayer");
    gray_out.style.visibility = "visible";
    setTimeout(addClass, 200);
}

function closeModal() {
    let modal = document.getElementById('modal');
    let gray_out = document.getElementById("fadeLayer");
    modal.classList.remove('is-show');
    gray_out.style.visibility ="hidden";
}

function addClass() {
    let modal = document.getElementById('modal');
    modal.classList.add('is-show');
}

function validateNumberInput(input) {
      input.value = input.value.replace(/[^0-9]/g, "");
 }

 function displayCurrentDateTime() {
       var currentDateTime = new Date();
       var year = currentDateTime.getFullYear();
       var month = String(currentDateTime.getMonth() + 1).padStart(2, '0');
       var day = String(currentDateTime.getDate()).padStart(2, '0');
       var formattedDateTime = `${year}-${month}-${day}`;
       document.getElementById("date").value = formattedDateTime;
 }

 function changeColor(area) {
   area.classList.toggle('highlight');
 }