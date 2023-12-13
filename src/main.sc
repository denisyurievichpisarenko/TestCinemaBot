require: films.csv
    name = films
    var = films

a: Привет! Я бот кинотеатра «...» Чем я могу вам помочь?
  
theme: /BuyTicket
    
    state: Hello
        a: Вы можете купить билет на сеанс или вернуть уже приобретённый. Что вы хотели бы сделать?
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