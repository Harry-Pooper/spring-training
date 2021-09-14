package main.java.controller;

import main.Dbc;
import main.java.dbo.UserContactsDbo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mrlz on 16.04.2018.
 */
@Controller
public class AdminController {

    public String uploadCities(){
        List<String> cities = new ArrayList<>();
        String city = "Абакан\n" +
                "Азов\n" +
                "Александров\n" +
                "Алексин\n" +
                "Альметьевск\n" +
                "Анапа\n" +
                "Ангарск\n" +
                "Анжеро-Судженск\n" +
                "Апатиты\n" +
                "Арзамас\n" +
                "Армавир\n" +
                "Арсеньев\n" +
                "Артем\n" +
                "Архангельск\n" +
                "Асбест\n" +
                "Астрахань\n" +
                "Ачинск\n" +
                "Балаково\n" +
                "Балахна\n" +
                "Балашиха\n" +
                "Балашов\n" +
                "Барнаул\n" +
                "Батайск\n" +
                "Белгород\n" +
                "Белебей\n" +
                "Белово\n" +
                "Белогорск (Амурская область)\n" +
                "Белорецк\n" +
                "Белореченск\n" +
                "Бердск\n" +
                "Березники\n" +
                "Березовский (Свердловская область)\n" +
                "Бийск\n" +
                "Биробиджан\n" +
                "Благовещенск (Амурская область)\n" +
                "Бор\n" +
                "Борисоглебск\n" +
                "Боровичи\n" +
                "Братск\n" +
                "Брянск\n" +
                "Бугульма\n" +
                "Буденновск\n" +
                "Бузулук\n" +
                "Буйнакск\n" +
                "Великие Луки\n" +
                "Великий Новгород\n" +
                "Верхняя Пышма\n" +
                "Видное\n" +
                "Владивосток\n" +
                "Владикавказ\n" +
                "Владимир\n" +
                "Волгоград\n" +
                "Волгодонск\n" +
                "Волжск\n" +
                "Волжский\n" +
                "Вологда\n" +
                "Вольск\n" +
                "Воркута\n" +
                "Воронеж\n" +
                "Воскресенск\n" +
                "Воткинск\n" +
                "Всеволожск\n" +
                "Выборг\n" +
                "Выкса\n" +
                "Вязьма\n" +
                "Гатчина\n" +
                "Геленджик\n" +
                "Георгиевск\n" +
                "Глазов\n" +
                "Горно-Алтайск\n" +
                "Грозный\n" +
                "Губкин\n" +
                "Гудермес\n" +
                "Гуково\n" +
                "Гусь-Хрустальный\n" +
                "Дербент\n" +
                "Дзержинск\n" +
                "Димитровград\n" +
                "Дмитров\n" +
                "Долгопрудный\n" +
                "Домодедово\n" +
                "Донской\n" +
                "Дубна\n" +
                "Евпатория\n" +
                "Егорьевск\n" +
                "Ейск\n" +
                "Екатеринбург\n" +
                "Елабуга\n" +
                "Елец\n" +
                "Ессентуки\n" +
                "Железногорск (Красноярский край)\n" +
                "Железногорск (Курская область)\n" +
                "Жигулевск\n" +
                "Жуковский\n" +
                "Заречный\n" +
                "Зеленогорск\n" +
                "Зеленодольск\n" +
                "Златоуст\n" +
                "Иваново\n" +
                "Ивантеевка\n" +
                "Ижевск\n" +
                "Избербаш\n" +
                "Иркутск\n" +
                "Искитим\n" +
                "Ишим\n" +
                "Ишимбай\n" +
                "Йошкар-Ола\n" +
                "Казань\n" +
                "Калининград\n" +
                "Калуга\n" +
                "Каменск-Уральский\n" +
                "Каменск-Шахтинский\n" +
                "Камышин\n" +
                "Канск\n" +
                "Каспийск\n" +
                "Кемерово\n" +
                "Керчь\n" +
                "Кинешма\n" +
                "Кириши\n" +
                "Киров (Кировская область)\n" +
                "Кирово-Чепецк\n" +
                "Киселевск\n" +
                "Кисловодск\n" +
                "Клин\n" +
                "Клинцы\n" +
                "Ковров\n" +
                "Когалым\n" +
                "Коломна\n" +
                "Комсомольск-на-Амуре\n" +
                "Копейск\n" +
                "Королев\n" +
                "Кострома\n" +
                "Котлас\n" +
                "Красногорск\n" +
                "Краснодар\n" +
                "Краснокаменск\n" +
                "Краснокамск\n" +
                "Краснотурьинск\n" +
                "Красноярск\n" +
                "Кропоткин\n" +
                "Крымск\n" +
                "Кстово\n" +
                "Кузнецк\n" +
                "Кумертау\n" +
                "Кунгур\n" +
                "Курган\n" +
                "Курск\n" +
                "Кызыл\n" +
                "Лабинск\n" +
                "Лениногорск\n" +
                "Ленинск-Кузнецкий\n" +
                "Лесосибирск\n" +
                "Липецк\n" +
                "Лиски\n" +
                "Лобня\n" +
                "Лысьва\n" +
                "Лыткарино\n" +
                "Люберцы\n" +
                "Магадан\n" +
                "Магнитогорск\n" +
                "Майкоп\n" +
                "Махачкала\n" +
                "Междуреченск\n" +
                "Мелеуз\n" +
                "Миасс\n" +
                "Минеральные Воды\n" +
                "Минусинск\n" +
                "Михайловка\n" +
                "Михайловск (Ставропольский край)\n" +
                "Мичуринск\n" +
                "Москва\n" +
                "Мурманск\n" +
                "Муром\n" +
                "Мытищи\n" +
                "Набережные Челны\n" +
                "Назарово\n" +
                "Назрань\n" +
                "Нальчик\n" +
                "Наро-Фоминск\n" +
                "Находка\n" +
                "Невинномысск\n" +
                "Нерюнгри\n" +
                "Нефтекамск\n" +
                "Нефтеюганск\n" +
                "Нижневартовск\n" +
                "Нижнекамск\n" +
                "Нижний Новгород\n" +
                "Нижний Тагил\n" +
                "Новоалтайск\n" +
                "Новокузнецк\n" +
                "Новокуйбышевск\n" +
                "Новомосковск\n" +
                "Новороссийск\n" +
                "Новосибирск\n" +
                "Новотроицк\n" +
                "Новоуральск\n" +
                "Новочебоксарск\n" +
                "Новочеркасск\n" +
                "Новошахтинск\n" +
                "Новый Уренгой\n" +
                "Ногинск\n" +
                "Норильск\n" +
                "Ноябрьск\n" +
                "Нягань\n" +
                "Обнинск\n" +
                "Одинцово\n" +
                "Озерск (Челябинская область)\n" +
                "Октябрьский\n" +
                "Омск\n" +
                "Орел\n" +
                "Оренбург\n" +
                "Орехово-Зуево\n" +
                "Орск\n" +
                "Павлово\n" +
                "Павловский Посад\n" +
                "Пенза\n" +
                "Первоуральск\n" +
                "Пермь\n" +
                "Петрозаводск\n" +
                "Петропавловск-Камчатский\n" +
                "Подольск\n" +
                "Полевской\n" +
                "Прокопьевск\n" +
                "Прохладный\n" +
                "Псков\n" +
                "Пушкино\n" +
                "Пятигорск\n" +
                "Раменское\n" +
                "Ревда\n" +
                "Реутов\n" +
                "Ржев\n" +
                "Рославль\n" +
                "Россошь\n" +
                "Ростов-на-Дону\n" +
                "Рубцовск\n" +
                "Рыбинск\n" +
                "Рязань\n" +
                "Салават\n" +
                "Сальск\n" +
                "Самара\n" +
                "Санкт-Петербург\n" +
                "Саранск\n" +
                "Сарапул\n" +
                "Саратов\n" +
                "Саров\n" +
                "Свободный\n" +
                "Севастополь\n" +
                "Северодвинск\n" +
                "Северск\n" +
                "Сергиев Посад\n" +
                "Серов\n" +
                "Серпухов\n" +
                "Сертолово\n" +
                "Сибай\n" +
                "Симферополь\n" +
                "Славянск-на-Кубани\n" +
                "Смоленск\n" +
                "Соликамск\n" +
                "Солнечногорск\n" +
                "Сосновый Бор\n" +
                "Сочи\n" +
                "Ставрополь\n" +
                "Старый Оскол\n" +
                "Стерлитамак\n" +
                "Ступино\n" +
                "Сургут\n" +
                "Сызрань\n" +
                "Сыктывкар\n" +
                "Таганрог\n" +
                "Тамбов\n" +
                "Тверь\n" +
                "Тимашевск\n" +
                "Тихвин\n" +
                "Тихорецк\n" +
                "Тобольск\n" +
                "Тольятти\n" +
                "Томск\n" +
                "Троицк\n" +
                "Туапсе\n" +
                "Туймазы\n" +
                "Тула\n" +
                "Тюмень\n" +
                "Узловая\n" +
                "Улан-Удэ\n" +
                "Ульяновск\n" +
                "Урус-Мартан\n" +
                "Усолье-Сибирское\n" +
                "Уссурийск\n" +
                "Усть-Илимск\n" +
                "Уфа\n" +
                "Ухта\n" +
                "Феодосия\n" +
                "Фрязино\n" +
                "Хабаровск\n" +
                "Ханты-Мансийск\n" +
                "Хасавюрт\n" +
                "Химки\n" +
                "Чайковский\n" +
                "Чапаевск\n" +
                "Чебоксары\n" +
                "Челябинск\n" +
                "Черемхово\n" +
                "Череповец\n" +
                "Черкесск\n" +
                "Черногорск\n" +
                "Чехов\n" +
                "Чистополь\n" +
                "Чита\n" +
                "Шадринск\n" +
                "Шали\n" +
                "Шахты\n" +
                "Шуя\n" +
                "Щекино\n" +
                "Щелково\n" +
                "Электросталь\n" +
                "Элиста\n" +
                "Энгельс\n" +
                "Южно-Сахалинск\n" +
                "Юрга\n" +
                "Якутск\n" +
                "Ялта\n" +
                "Ярославль";
        cities = Arrays.asList(city.split("[\n]"));
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            for (String string : cities) {
                stmt = c.createPreparedStatement(
                        "INSERT INTO CITIES VALUES(GENERATE_ID(), 2461, ?)"
                );
                stmt.setString(1, string);
                stmt.executeUpdate();
            }
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return "redirect:/";
    }

    public String updatePositions(){
        String pos = "HTML-верстальщик\n" +
                "Web-интегратор\n" +
                "Администратор базы данных\n" +
                "Блогер\n" +
                "Веб-дизайнер\n" +
                "Диктор\n" +
                "Контент-менеджер\n" +
                "Копирайтер\n" +
                "Радиоведущий\n" +
                "Системный администратор";
        List<String> positions = Arrays.asList(pos.split("[\n]"));
        Dbc c = new Dbc();
        PreparedStatement stmt;
        c.connect();
        try{
            for (String string : positions) {
                stmt = c.createPreparedStatement(
                        "INSERT INTO position VALUES(GENERATE_ID(), ?)"
                );
                stmt.setString(1, string);
                stmt.executeUpdate();
            }
            c.commit();
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return "redirect:/";
    }

}
