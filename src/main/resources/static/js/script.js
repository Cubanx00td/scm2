console.log("Script Loaded");

let currentTheme = getTheme();

//initial
document.addEventListener('DOMContentLoaded', () => {
    changeTheme(currentTheme);
});




//set theme to localstorage
function setTheme(theme){
    localStorage.setItem("theme", theme);
}

//get theme from localstorage
function getTheme(){
    let theme = localStorage.getItem("theme");
    if(theme != null){
        return theme;
    }
    else{
        return "light";
    }
}

function changeTheme(){
    //set to web page
    changePageTheme(currentTheme, currentTheme);

    //set the listener to change theme button
    const changeThemeButton = document.querySelector('#theme_change_button');
    
    changeThemeButton.addEventListener("click", (event) => {
        console.log("change theme button clicked");
        let oldTheme = currentTheme;
        
        if(currentTheme == "dark"){
            currentTheme = "light";
        }
        else{
            currentTheme = "dark";
        }
        changePageTheme(currentTheme, oldTheme);
    });
    
}

function changePageTheme(theme, oldTheme){

    // update in local storage
    setTheme(currentTheme);
        
    //remove the current theme
    document.querySelector('html').classList.remove(oldTheme);

    //set the current theme
    document.querySelector('html').classList.add(theme);

    //change text of button
    document
        .querySelector("#theme_change_button")
        .querySelector("span").textContent = currentTheme == "light" ? "Dark" : "Light";
}