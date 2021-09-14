package main.java;

/**
 * Created by mrlz on 31.03.2018.
 */
public class Dict {
    public static final Long DEGREE_TYPES = 61L;
    public static final Long PAYMENT_TYPES = 91L;
    public static final Long YES_NO = 121L;
    public static final Long READY_TO_BUSINESS_TRIP = 481L;
    public static final Long ACC_TYPE = 631L;
    public static final Long CONTACT_TYPE = 1621L;

    //Типы образований
    public static final Long UNIVERSITY_DEGREE = 151L; //Высшее образование
    public static final Long NON_COMPLETE_UNIVERSITY_DEGREE = 181L; //Не оконченное высшее
    public static final Long SCHOOL_DEGREE = 211L; //Среднее образование
    public static final Long NON_COMPLETE_SCHOOL_DEGREE = 241L; //Не оконченное среднее
    public static final Long NO_DEGREE = 271L; //Без образования

    //Типы зарплат
    public static final Long FROM = 301L; //От
    public static final Long TO = 331L; //До
    public static final Long FROM_TO = 361L; //От и до (Вилка)
    public static final Long NOT_DETERMINED = 391L; //Не указано

    //Готовность к командировкам
    public static final Long SHORT_TERM = 511L; //Краткосрочные
    public static final Long LONG_TERM = 541L; //Длительные
    public static final Long RARE_BT = 571L; //Редкие
    public static final Long OFTEN_BT = 601L; //Частые
    public static final Long NO_BT = 602L; //Без коммандировок

    //Да/нет
    public static final Long YES = 421L;
    public static final Long NO = 451L;

    //Типы аккаунтов
    public static final Long USER = 661L;
    public static final Long ORG = 691L;

    //Типы контактов
    public static final Long EMAIL = 1651L;
    public static final Long PHONE = 1681L;
    public static final Long FAX = 1711L;

    //Типы организаций
    public static final Long OOO_TYPE = 13141L; //ООО
    public static final Long IP_TYPE = 13171L; //ИП
    public static final Long ODO_TYPE = 13201L; //ОДО
    public static final Long PT_TYPE = 13231L; //Полное товарищество
    public static final Long TV_TYPE = 13291L; //Товарищество на вере
    public static final Long PK_TYPE = 13261L; //Производственный кооперати

    //Статусы рассмотра резюме
    public static final Long SENT = 13262L; //на рассмотрении
    public static final Long REJECT = 13263L; //Отклонено
    public static final Long ACCEPTED = 13264L; //Приглашён на собеседование

    //Количество работников
    public static final Long min50_TYPE = 13591L; //Менее 50 сотрудников
    public static final Long min100_TYPE = 136211L; //от 50 до 100 сотрудников
    public static final Long min500_TYPE = 13651L; //от 100 до 500 сотрудников
    public static final Long min1000_TYPE = 13681L; //от 500 до 1000 сотрудников
    public static final Long min10000_TYPE = 13711L; //от 1000 до 1000000 сотрудников
}
