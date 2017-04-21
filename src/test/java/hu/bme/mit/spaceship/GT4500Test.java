package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockTS, mockTS1;
  
  @Before
  public void init(){
	mockTS = mock(TorpedoStore.class);
	mockTS1 = mock(TorpedoStore.class);
    this.ship = new GT4500(mockTS, mockTS1);
  }

  @Test
  public void fireTorpedos_Single_Success(){
    // Arrange
    when(mockTS.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);
    ship.fireLasers(FiringMode.SINGLE);
    // Assert
    verify(mockTS, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedos_All_Success(){
    // Arrange
    when(mockTS.fire(mockTS.getNumberOfTorpedos())).thenReturn(true);
    when(mockTS1.fire(mockTS1.getNumberOfTorpedos())).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);

    // Assert
    verify(mockTS, times(2)).fire(mockTS.getNumberOfTorpedos());
    verify(mockTS1, times(2)).fire(mockTS1.getNumberOfTorpedos());
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedos_wasPrimaryFiredLast_Check(){
    // Arrange
    when(mockTS.fire(1)).thenReturn(true);
    when(mockTS1.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedos(FiringMode.SINGLE);
    boolean result3 = ship.isPrimaryFiredLast();
    
    // Assert
    verify(mockTS, times(1)).fire(1);
    verify(mockTS1, times(1)).fire(1);
    assertEquals(false, result3);
  }
  
  @Test
  public void fireAllTorpedosTwice(){
    // Arrange
    when(mockTS.fire(0)).thenReturn(false);
    when(mockTS1.fire(0)).thenReturn(false);
    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);
    boolean result2 = ship.fireTorpedos(FiringMode.ALL);
    // Assert
    verify(mockTS, times(2)).fire(mockTS.getNumberOfTorpedos());
    verify(mockTS1, times(2)).fire(mockTS1.getNumberOfTorpedos());
    assertEquals(false, result2);
    
  }
  
  @Test
  public void fireTorpedos_empty_primaryStore(){
    // Arrange
    when(mockTS.fire(mockTS.getNumberOfTorpedos())).thenReturn(true);
    when(mockTS1.fire(mockTS1.getNumberOfTorpedos())).thenReturn(true);
    when(mockTS.isEmpty()).thenReturn(true);
    when(mockTS1.isEmpty()).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);
    
    // Assert
    verify(mockTS, times(1)).fire(mockTS.getNumberOfTorpedos());
    assertEquals(true, result && ship.primaryStoreIsEmpty() && ship.secondaryStoreIsEmpty());
    
  }
  
  @Test
  public void fireTorpedos_check_Single(){
    // Arrange
    when(mockTS1.fire(1)).thenReturn(true);
    when(mockTS.isEmpty()).thenReturn(true);
    when(mockTS1.isEmpty()).thenReturn(false);
    // Act
    boolean success = ship.fireTorpedos(FiringMode.SINGLE);
    
    // Assert
    verify(mockTS1, times(1)).fire(1);
    assertEquals(true, success);
    
  }
  
  @Test
  public void fireTorpedos_check_Single2(){
    // Arrange
    when(mockTS.fire(1)).thenReturn(true);
    when(mockTS.isEmpty()).thenReturn(false);
    when(mockTS1.isEmpty()).thenReturn(true);
    // Act
    /*while(!ship.secondaryStoreIsEmpty())
    	ship.fireTorpedos(FiringMode.SINGLE);*/
    

    ship.fireTorpedos(FiringMode.SINGLE);
    boolean success = ship.fireTorpedos(FiringMode.SINGLE);
    
    // Assert
    verify(mockTS, times(2)).fire(1);
    assertEquals(true, success);
    
  }
  
}
