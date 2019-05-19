package com.gg.simplenumbers.presentation.numberslist.list

/**
 * Created by ggaworowski on 18.05.2019.
 */
interface NumberListItem {
    class NumberItem(val number:Int) : NumberListItem{
        override fun equals(other: Any?): Boolean {
            if(other is NumberItem) return other.number == this.number
            return super.equals(other)
        }

        override fun hashCode(): Int {
            return number
        }
    }
    object LoadMoreItem : NumberListItem
}