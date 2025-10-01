const toggler = document.querySelector(".btn");
const sidebar = document.querySelector("#sidebar");
const mainContent = document.querySelector(".main");


sidebar.classList.add("collapsed"); 
mainContent.style.marginLeft = "0";  

// Configuramos el evento para alternar entre colapsado y expandido
toggler.addEventListener("click", function() {
    sidebar.classList.toggle("collapsed");

    if (sidebar.classList.contains("collapsed")) {
        mainContent.style.marginLeft = "0";  
    } else {
        mainContent.style.marginLeft = "264px"; 
    }
});