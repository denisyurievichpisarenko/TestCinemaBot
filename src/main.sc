require: films.csv
    name = films
    var = films

theme: /BuyTicket
    
    state: ChooseTitle
        
        a: Выберите фильм.
        script:
            for (var id = 1; id < Object.keys(films).length + 1; id++) {
                var titles = films[id].value.title;
                {
                    var button_name = films[id].value.title;
                    $reactions.buttons({text: button_name, transition: 'GetTitle'})
                }
            }
        
    state: GetTitle
        script:
            $session.film_title = $request.query;
        go!: /ChooseTime
    
    state: ChooseTime
        
        a: Выберите время сеанса.
        script:
            for (var id = 1; id < Object.keys(films).length + 1; id++) {
                var timeslots = films[id].value.time;
                if (_.contains(timeslots, $session.film_title)) {
                    var button_name = films[id].value.time;
                    $reactions.buttons({text: button_name, transition: 'GetTime'})
                }
            }
        
    state: GetTime
        script:
            $session.film_time = $request.query;
        go!: /Choo
        
        
        
        a: 
        buttons:
            "Купить" -> /BuyTicket/Buy
            "Вернуть" -> /BuyTicket/Refund
        
        state: Buy
            a: Введите название фильма
            
        
        state: Refund
            a: Чтобы купить билет, перейдите по ссылке:
    
    state: CatchAll
        event!: noMatch
        a: Извините, я вас не понимаю.