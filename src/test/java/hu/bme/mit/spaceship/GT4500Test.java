package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockTS;
  
  @Before
  public void init(){
	mockTS = mock(TorpedoStore.class);
    this.ship = new GT4500(mockTS);
  }

  @Test
  public void fireTorpedos_Single_Success(){
    // Arrange
    when(mockTS.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    verify(mockTS, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedos_All_Success(){
    // Arrange
    when(mockTS.fire(mockTS.getNumberOfTorpedos())).thenReturn(true);
    
    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);

    // Assert
    verify(mockTS, times(3)).fire(mockTS.getNumberOfTorpedos());
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedos_wasPrimaryFiredLast_Check(){
    // Arrange
    when(mockTS.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedos(FiringMode.SINGLE);
    boolean result3 = ship.isPrimaryFiredLast();
    
    // Assert
    verify(mockTS, times(2)).fire(1);
    assertEquals(false, result3);
  }
  
  @Test
  public void fireAllTorpedosTwice(){
    // Arrange
    when(mockTS.fire(mockTS.getNumberOfTorpedos())).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);
    boolean result2 = ship.fireTorpedos(FiringMode.ALL);
    // Assert
    verify(mockTS, times(5)).fire(mockTS.getNumberOfTorpedos());
    assertEquals(true, result2);
    
  }
  
  @Test
  public void fireTorpedos_empty_primaryStore(){
    // Arrange
    when(mockTS.fire(mockTS.getNumberOfTorpedos())).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);
    
    // Assert
    verify(mockTS, times(3)).fire(mockTS.getNumberOfTorpedos());
    assertEquals(false, result && ship.primaryStoreIsEmpty() && ship.secondaryStoreIsEmpty());
    
  }
  
  
}
