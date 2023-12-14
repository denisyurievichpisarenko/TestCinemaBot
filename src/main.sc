require: films.csv
    name = films
    var = films

theme: /
    
    state: Initialize
        q!: $regex</start>
        script:
            $context.session = {};
            $context.client = {};
            $context.temp = {};
            $context.response = {};
        a: Привет! Это бот кинотеатра "Аргунь". Здесь можно купить билет на сеанс, а скоро можно будет что-нибудь ещё. 
        a: Какой у вас вопрос?
        buttons: 
            "Купить билет" -> ./Start
            "Что-нибудь ещё" -> /AnythingElse
    
        state: Start
            a: Здесь вы можете приобрести билет на сеанс
            buttons:
                "Начать" -> ./ChooseDay
                "В меню" -> ..
        
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
                    go!: ../ChooseFilm
            
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
                        go!: ../ChooseTime
            
                    state: ChooseTime
                        
                        a: Выберите время.
                        script:
                            for (var id = 1; id < Object.keys(films).length + 1; id++) {
                                if ($session.film_date == films[id].value.date) {
                                    var films_to_watch = films[id].value.films;
                                    for(var i = 0; i < films_to_watch.length; i++){
                                            if ($session.film_title == films_to_watch[i].title){
                                                var times = films_to_watch[i].time;
                                                for(var j = 0; j < times.length; j++){
                                                    var button_name = times[j]
                                                    $reactions.buttons({text: button_name, transition: 'GetTime'})
                                                    }
                                                }
                                        }
                                }
                            }
            
                        state: GetTime
                            script:
                                $session.film_title = $request.query;
                            go!: ../GiveLink
            
                        state: GiveLink
                            a: Для выбора мест и оплаты воспользуйтесь ссылкой ниже. Для возвращения в меню нажмите "Вернуться"
                            buttons:
                                {text:"Перейти на сайт", url:"https://www.youtube.com/watch?v=dQw4w9WgXcQ"}
                                "Вернуться" -> /Initialize
    
    state: AnythingElse
        a: Ой. Я пока умею только продавать билеты :(
        buttons:
            "Вернуться в меню?" -> ../Initialize