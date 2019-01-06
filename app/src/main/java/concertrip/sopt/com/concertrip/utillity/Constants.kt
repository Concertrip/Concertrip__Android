package concertrip.sopt.com.concertrip.utillity

import concertrip.sopt.com.concertrip.R

class Constants {
    companion object {
        const  val LOG_NETWORK ="~~~~Network"

        //Tablayout의 아이템
        const val TAB_CALENDAR = 0
        const val TAB_SEARCH= 1
        const val TAB_LIKED = 2
        const val TAB_MY_PAGE = 3



        //CalendarAdapter의 viewType
        const val CALENDAR_TYPE_DAY = 0
        const val CALENDAR_TYPE_BLANK = 1
        const val CALENDAR_TYPE_DATE = 2


        const  val TAB_ARTIST =0
        const  val TAB_GENRE =1
        const  val TAB_CONCERT =2


        //BasicListAdapter의 viewType
        const val TYPE_ARTIST = 0
        const val TYPE_CONCERT = 1
        const val TYPE_GENRE = 2
        const val TYPE_TICKET = 3
        const val TYPE_ALARM = 4
        const val TYPE_CAUTION = 5
        const val TYPE_CALENDAR_TAG = 6
        const val TYPE_SCHEDULE = 7



        //FragmentAdapter의 배열idx와 맞춰야함
        const val FRAGMENT_CALENDAR = 0
        const val FRAGMENT_EXPLORER= 1
        const val FRAGMENT_LIKED = 2
        const val FRAGMENT_MY_PAGE = 3
        const val FRAGMENT_TICKET_LIST = 4
        const val FRAGMENT_SEARCH = 5
        const val FRAGMENT_TICKET = 6
        const val FRAGMENT_SETTING =7


        const val INTENT_TAG_ID = "id"

        const val INTENT_ARTIST = "artist"

        //CalendarFragment에서 networkPath 지정할때
        const val TYPE_MONTH = 0
        const val TYPE_DAY = 1


        //테스용 유저 TOKEN값
        const val USER_TOKEN=1

    }
}