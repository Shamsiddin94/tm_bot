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

//State control object
var centerData={
    id:null,
    state:false,
    ids:[],
    }


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


    if(centerData.id && centerData.state){
        var parentElement=document.getElementById(centerData.id);
        parentElement.scrollIntoView({block: "center", behavior: "smooth"});
        centerData.state=false;
    }else {
        document.body.scrollTop = 0;
        document.documentElement.scrollTop = 0;

    }
        console.log(centerData);

}


function handleButtonClick(e,item) {
     if (item){
        let parentId=item.parentNode.id;
         centerData.state=!centerData.state;
         centerData.id= parentId;
         centerData.ids.push(parentId);
             //console.log(centerData);
            // console.log("Item passed");

             }



    for (i = 1; i <= 28; i++) {
        var a = document.getElementById(i).classList.remove("planTr");
    }

    var doc = document.getElementById(e);
    doc.classList.add("planTr");
    doc.scrollIntoView({block: "center", behavior: "smooth"});


}

