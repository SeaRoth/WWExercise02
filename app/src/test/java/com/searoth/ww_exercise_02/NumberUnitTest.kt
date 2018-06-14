package com.searoth.ww_exercise_02

import com.searoth.ww_exercise_02.model.Contact
import org.junit.Test

import org.junit.Assert.*

import org.mockito.Mockito.`when` as _when

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class NumberUnitTest {

    @Test
    fun `valid number`(){
        var c = Contact(0,"monkaS", "425-260-6800")
        var res = Contact.validateNumber(c.number)
        assertTrue(res)

        //single letter name
        //no dashes, full number
        c = Contact(0, "d", "4252606800")
        res = Contact.validateNumber(c.number)
        assertTrue(res)

        //no name
        //number ok
        c = Contact(0, "", "4252606800")
        res = Contact.validateNumber(c.number)
        assertTrue(res)

        //number ok
        c = Contact(0, "3434343434 3434", "(425)260-6800")
        res = Contact.validateNumber(c.number)
        assertTrue(res)

        //number ok
        c = Contact(0, "Ray Lewis", "1(425)260-6800")
        res = Contact.validateNumber(c.number)
        assertTrue(res)

        //number ok
        c = Contact(0, "Ray Lewis", "+1(425)260-6800")
        res = Contact.validateNumber(c.number)
        assertTrue(res)

        /**
         * FALSE
         */
        //normal name
        //number bad
        c = Contact(0, "Dwayne The Rock Johnson", "425260680033")
        res = Contact.validateNumber(c.number)
        assertFalse(res)

        //number bad
        c = Contact(0, "Dwayne The Rock Johnson", "+10(425)260-6800")
        res = Contact.validateNumber(c.number)
        assertFalse(res)

        //number bad
        c = Contact(0, "Dwayne The Rock Johnson", "+10-(425)-260-6800")
        res = Contact.validateNumber(c.number)
        assertFalse(res)

        //number bad
        c = Contact(0, "Dwayne The Rock Johnson", "+dddss")
        res = Contact.validateNumber(c.number)
        assertFalse(res)
    }
}
