package concertrip.sopt.com.concertrip.model

import kotlin.properties.Delegates

class Schedule(var date: Int, var text: String) {
    var position : Int by Delegates.notNull() //dayList 상에서 몇번째 위치했는지

    //이건 임시 변수



    companion object {

        fun getDummy(date: Int): Schedule = Schedule(date, "지코")
        fun getDummyList(): ArrayList<Schedule> {

            val list = ArrayList<Schedule>()
            list.add(Schedule(1, "지코"))
            list.add(Schedule(3, "피오"))
            list.add(Schedule(7, "지코"))

            list.add(Schedule(1, "지코"))
            list.add(Schedule(3, "피오"))
            list.add(Schedule(7, "지코"))

            list.add(Schedule(1, "지코"))
            list.add(Schedule(3, "피오"))
            list.add(Schedule(7, "지코"))

            list.add(Schedule(1, "지코"))
            list.add(Schedule(3, "피오"))
            list.add(Schedule(7, "지코"))

            list.add(Schedule(1, "지코"))
            list.add(Schedule(3, "피오"))
            list.add(Schedule(7, "지코"))

            list.add(Schedule(1, "지코"))
            list.add(Schedule(3, "피오"))
            list.add(Schedule(7, "지코"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))
            list.add(Schedule(12, "피오"))


            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))
            list.add(Schedule(28, "윤딴딴"))

            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            list.add(Schedule(29, "마인드유"))
            return list


        }
        fun getDummyMap() : HashMap<Int, ArrayList<Schedule>>{
            return toMap(getDummyList())
        }
        fun toMap(list : ArrayList<Schedule>) : HashMap<Int, ArrayList<Schedule>>{
            val  map  = HashMap<Int, ArrayList<Schedule>>()
            list.forEach {
                if(!map.containsKey(it.date))
                    map[it.date]= ArrayList<Schedule>()
                map[it.date]?.add(it)
            }
            return map
        }
    }

}