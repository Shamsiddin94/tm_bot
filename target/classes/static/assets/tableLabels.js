// Call the dataTables jQuery plugin

$(document).ready(function() {
  $('#exam6 ').DataTable({
    stateSave: true,
    "order": [[ 3, 'desc' ]],
    "aLengthMenu": [
      [10, 25,  100, -1],
      [10,25, 100, "Барчаси"]
    ],

    "language": {
      "emptyTable": "Маьлумот мавжуд эмас!",
      "search": "Қидириш",
      "sLengthMenu": "Кўрсатиш _MENU_ сони",
      "info": "Кўрсатилди- _TOTAL_,  Жами-_MAX_",
      paginate: {
        next: '&#8594;', // or '→'
        previous: '&#8592;' // or '←'
      }
    },

  });
});

$(document).ready(function() {
  $('#exam5 ').DataTable({
    stateSave: true,
    "order": [[ 3, 'desc' ]],
    "aLengthMenu": [
      [10, 25,  100, -1],
      [10,25, 100, "Барчаси"]
    ],
    "language": {
      "search": "Қидириш",
      "infoEmpty": "Маьлумот мавжуд эмас!",
      "sLengthMenu": "Кўрсатиш _MENU_ сони",
      "info": "Кўрсатилди- _TOTAL_,  Жами-_MAX_",
      paginate: {
        next: '&#8594;', // or '→'
        previous: '&#8592;' // or '←'
      }
    },

  });
});
$(document).ready(function() {
  stateSave: true,
  $('#exam4 ').DataTable({
    "order": [[ 1, 'desc' ]],
    "aLengthMenu": [
      [10, 25,  100, -1],
      [10,25, 100, "Барчаси"]
    ],
    "language": {
      "search": "Қидириш",
      "emptyTable": "Маьлумот мавжуд эмас!",
      "sLengthMenu": "Кўрсатиш _MENU_ сони",
      "info": "Кўрсатилди- _TOTAL_,  Жами-_MAX_",
      paginate: {
        next: '&#8594;', // or '→'
        previous: '&#8592;' // or '←'
      }
    },

  });
});

$(document).ready(function() {
  $('#exam3 ').DataTable({
    stateSave: true,
    "order": [[ 1, 'desc' ]],
    "aLengthMenu": [
      [10, 25,  100, -1],
      [10,25, 100, "Барчаси"]
    ],
    "language": {
      "search": "Қидириш",
      "emptyTable": "Маьлумот мавжуд эмас!",
      "sLengthMenu": "Кўрсатиш _MENU_ сони",
      "info": "Кўрсатилди- _TOTAL_,  Жами-_MAX_",
      paginate: {
        next: '&#8594;', // or '→'
        previous: '&#8592;' // or '←'
      }
    },

  });
});
$(document).ready(function() {
  stateSave: true,
  $('#exam2 ').DataTable({
    "order": [[ 1, 'desc' ]],
    "aLengthMenu": [
      [10, 25,  100, -1],
      [10,25, 100, "Барчаси"]
    ],
    "language": {
      "search": "Қидириш",
      "emptyTable": "Маьлумот мавжуд эмас!",
      "infoEmpty": "  Маьлумот мавжуд эмас.",
      "sLengthMenu": "Кўрсатиш _MENU_ сони",
      "info": "Кўрсатилди- _TOTAL_,  Жами-_MAX_",
      paginate: {
        next: '&#8594;', // or '→'
        previous: '&#8592;' // or '←'
      }
    },

  });
});
$(document).ready(function() {
  $('#exam1 ').DataTable({
    stateSave: true,
    "aLengthMenu": [
      [10, 25,  100, -1],
      [10,25, 100, "Барчаси"]
    ],
    "order": [[ 1, 'desc' ]],
    "language": {
      "search": "Қидириш",
      "emptyTable": "Маьлумот мавжуд эмас!",
      "sLengthMenu": "Кўрсатиш _MENU_ сони",
      "info": "Кўрсатилди- _TOTAL_,  Жами-_MAX_",
      paginate: {
        next: '&#8594;', // or '→'
        previous: '&#8592;' // or '←'
      }
    },

  });
});