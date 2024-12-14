package com.demo.dto;


import com.demo.domain.entity.Count;
import com.demo.domain.exceptions.NotEnoughAmountException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//Unitary test of DTO implements TDD
public class CountTest {

    @Test
    void testCount() {
        Count count = new Count(1L,"Alejandro",12);
        count.setPerson("Alejandro");
        String wantedPerson = "Alejandro";
        String realPerson = count.getPerson();
        Assertions.assertEquals(wantedPerson, realPerson);
    }

    @Test
    void testAmount(){
        Count count = new Count(1L,"Alejandro",12);
        Assertions.assertEquals(count.getPerson(),"Alejandro");
        Assertions.assertEquals(count.getAmount(),12);
    }

    @Test
    void testDebit(){
        Count count = new Count(1L,"Alejandro",12);
        count.debit(2);
        Assertions.assertEquals(10,count.getAmount());
    }

    @Test
    void testCredit(){
        Count count = new Count(1L,"Alejandro",12);
        count.credit(2);
        Assertions.assertEquals(14,count.getAmount());
    }

    @Test
    void testNotEnoughAmountException(){
        Count count = new Count(1L,"Alejandro",12);
        Exception exception = Assertions.assertThrows(NotEnoughAmountException.class, ()->{
            count.debit(15);
        });
        String wantedMessage = exception.getMessage();
        Assertions.assertEquals("Not enough amount", wantedMessage);
    }
}
