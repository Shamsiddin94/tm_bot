Vue.component('alert-box',{
    props:['title'],
    data:function () {
            return {

            }
    },
    mounted(){},
    methods:{},
    template:'<div class="demo-alert-box">\n' +
        '      <strong>Error!</strong>\n' +
        '      <slot>{{title}}</slot>\n' +
        '    </div>'

});