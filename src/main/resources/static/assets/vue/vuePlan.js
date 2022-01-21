/*


var app=new Vue({
    el:"#app",
    data:{

    },
    mounted(){
        console.log('salom');
        var cont=document.getElementById("spanAll");
        var spans=cont.getElementsByTagName("span");
        console.log(spans);
    }
    ,
    methods:{

    }



});
*/
//Knopka idisi
var mybutton = document.getElementById("myBtn");

// Sahifada ko'rinadi
window.onscroll = function () {
    scrollFunction()
};

function scrollFunction() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        mybutton.style.display = "block";
    } else {
        mybutton.style.display = "none";
    }
}

// yuqoriga knopka
function topFunction() {
    for (i = 1; i <= 28; i++) {
        var a = document.getElementById(i).classList.remove("planTr");
    }

    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}


function handleButtonClick(e) {

    for (i = 1; i <= 28; i++) {
        var a = document.getElementById(i).classList.remove("planTr");
    }

    var doc = document.getElementById(e);
    doc.classList.add("planTr");
    doc.scrollIntoView({block: "center", behavior: "smooth"});


}

