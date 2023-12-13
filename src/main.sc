require: films.csv
    name = films
    var = films

theme: /BuyTicket
    
    state: Hello
        
        a: Вы можете купить билет на сеанс или вернуть уже приобретённый. Что вы хотели бы сделать?
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
            $session.pizza_name = $request.query;
        go!: /Choose
        
        
        
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