require: films.csv
    name = films
    var = films

theme: /
    
    state: Start
        q!: $regex</start>
        script:
            $context.session = {};
            $context.client = {};
            $context.temp = {};
            $context.response = {};
        a: Здесь вы можете приобрести билет на сеанс
        go!: /ChooseDay
    
    state: ChooseDay
        
        a: Выберите день.
        script:
            for (var id = 1; id < Object.keys(films).length + 1; id++) {
                    var button_name = films[id].value.date;
                    $reactions.buttons({text: button_name, transition: 'GetDate'})
                }
        
        state: GetDate
            script:
                $session.film_date = $request.query;
            go!: /ChooseFilm
    
    state: ChooseFilm
        
        a: Выберите фильм.
        script:
            for (var id = 1; id < Object.keys(films).length + 1; id++) {
                if ($session.film_date == films[id].value.date) {
                    var films_to_watch = films[id].value.films;
                    for(var i = 0; i < films_to_watch.length; i++){
                            var button_name = films_to_watch[i].title
                            $reactions.buttons({text: button_name, transition: 'GetTitle'})
                        }
                }
            }
        
        state: GetTitle
            script:
                $session.film_title = $request.query;
            go!: /GiveLink
    
    state: GiveLink
        a: Для выбора мест и оплаты воспользуйтесь ссылкой:
        inlineButtons:
            {text:"Перейти на сайт", url:"https://www.youtube.com/watch?v=dQw4w9WgXcQ"}