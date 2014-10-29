package org.plos.crepo.model.validator;

import org.junit.Before;
import org.junit.Test;
import org.plos.crepo.exceptions.ContentRepoException;
import org.plos.crepo.exceptions.ErrorType;
import org.plos.crepo.model.RepoObject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class RepoObjectValidatorTest {

  private RepoObjectValidator repoObjectValidator;
  private static String KEY = "testKey";

  @Before
  public void setUp(){
    repoObjectValidator = new RepoObjectValidator();
  }

  @Test
  public void valideRepoObjectTest(){

    RepoObject repoObject = mock(RepoObject.class);

    when(repoObject.getFileContent()).thenReturn(null);
    when(repoObject.getByteContent()).thenReturn(new byte[1]);
    when(repoObject.getContentType()).thenReturn("text/plain");

    repoObjectValidator.validate(repoObject);

    verify(repoObject).getFileContent();
    verify(repoObject, times(2)).getByteContent();
    verify(repoObject).getContentType();

  }

  @Test
  public void emptyContentRepoObjectTest(){

    RepoObject repoObject = mock(RepoObject.class);

    when(repoObject.getFileContent()).thenReturn(null);
    when(repoObject.getByteContent()).thenReturn(null);

    try{
      repoObjectValidator.validate(repoObject);
      fail("A content repo app was expected. ");
    } catch(ContentRepoException e){
      assertEquals(ErrorType.EmptyContent, e.getErrorType());
      verify(repoObject).getFileContent();
      verify(repoObject).getByteContent();
    }

  }

  @Test
  public void emptyContentTypeRepoObjectTest(){

    RepoObject repoObject = mock(RepoObject.class);

    when(repoObject.getFileContent()).thenReturn(null);
    when(repoObject.getByteContent()).thenReturn(new byte[1]);
    when(repoObject.getContentType()).thenReturn(null);
    when(repoObject.getKey()).thenReturn(KEY);

    try{
      repoObjectValidator.validate(repoObject);
      fail("A content repo app was expected. ");
    } catch(ContentRepoException e){
      assertEquals(ErrorType.EmptyContentType, e.getErrorType());
      assertTrue(e.getMessage().contains(KEY));
      verify(repoObject).getFileContent();
      verify(repoObject, times(2)).getByteContent();
      verify(repoObject).getKey();
    }

  }

}
