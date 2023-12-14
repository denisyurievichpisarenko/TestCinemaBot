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
        go!: /ChooseTitle
    
    state: ChooseTitle
        
        a: Выберите фильм.
        script:
            for (var id = 1; id < Object.keys(films).length + 1; id++) {
                    var button_name = films[id].value.title;
                    $reactions.buttons({text: button_name, transition: 'GetTitle'})
                }
        
        state: GetTitle
            script:
                $session.film_title = $request.query;
            go!: /ChooseTime
    
    state: ChooseTime
        
        a: Выберите время сеанса.
        script:
            for (var id = 1; id < Object.keys(films).length + 1; id++) {
                if ($session.film_title == films[id].value.title) {
                    var button_name = films[id].value.time;
                    $reactions.buttons({text: button_name, transition: 'GetTime'})
                }
            }
        
        state: GetTime
            script:
                $session.film_time = $request.query;
            go!: /GiveLink
    
    state: GiveLink
        a: Для выбора мест и оплаты воспользуйтесь ссылкой:
        inlineButtons:
            {text:"Перейти на сайт", url:"https://www.youtube.com/watch?v=dQw4w9WgXcQ"}