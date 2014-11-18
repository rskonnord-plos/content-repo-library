package org.plos.crepo.dao.objects.impl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.plos.crepo.config.ContentRepoAccessConfig;
import org.plos.crepo.dao.ContentRepoBaseDao;
import org.plos.crepo.dao.objects.ContentRepoObjectDao;
import org.plos.crepo.exceptions.ErrorType;
import org.plos.crepo.model.CreationMethod;
import org.plos.crepo.model.RepoObject;
import org.plos.crepo.util.ObjectUrlGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ContentRepoObjectDaoImpl extends ContentRepoBaseDao implements ContentRepoObjectDao {

  private static final Logger log = LoggerFactory.getLogger(ContentRepoObjectDaoImpl.class);

  public ContentRepoObjectDaoImpl(ContentRepoAccessConfig accessConfig) {
    super(accessConfig);
  }

  @Override
  public HttpResponse getLatestRepoObj(String bucketName, String key) {

    HttpGet request = new HttpGet(ObjectUrlGenerator.getLatestObjectUrl(getRepoServer(), bucketName, key));
    return executeRequest(request, ErrorType.ErrorFetchingObject);

  }

  @Override
  public HttpResponse getRepoObjUsingVersionCks(String bucketName, String key, String versionChecksum) {

    HttpGet request = new HttpGet(ObjectUrlGenerator.getObjectUsingVersionCksUrl(getRepoServer(), bucketName, key, versionChecksum));
    return executeRequest(request, ErrorType.ErrorFetchingObject);

  }

  @Override
  public HttpResponse getRepoObjUsingVersionNum(String bucketName, String key, int versionNumber) {

    HttpGet request = new HttpGet(ObjectUrlGenerator.getObjectUsingVersionNumUrl(getRepoServer(), bucketName, key, versionNumber));
    return executeRequest(request, ErrorType.ErrorFetchingObject);

  }

  @Override
  public HttpResponse getRepoObjMetaLatestVersion(String bucketName, String key) {
    HttpGet request = new HttpGet(ObjectUrlGenerator.getLatestObjectMetaUrl(getRepoServer(), bucketName, key));
    return executeRequest(request, ErrorType.ErrorFetchingObjectMeta);
  }

  @Override
  public HttpResponse getRepoObjMetaUsingVersionChecksum(String bucketName, String key, String versionChecksum) {
    HttpGet request = new HttpGet(ObjectUrlGenerator.getObjectMetaUsingVersionCksUrl(getRepoServer(), bucketName, key, versionChecksum));
    return executeRequest(request, ErrorType.ErrorFetchingObjectMeta);
  }

  @Override
  public HttpResponse getRepoObjMetaUsingVersionNumber(String bucketName, String key, int versionNumber) {
    HttpGet request = new HttpGet(ObjectUrlGenerator.getObjectMetaUsingVersionNumUrl(getRepoServer(), bucketName, key, versionNumber));
    return executeRequest(request, ErrorType.ErrorFetchingObjectMeta);
  }

  @Override
  public HttpResponse getRepoObjVersionsMeta(String bucketName, String key) {
    HttpGet request = new HttpGet(ObjectUrlGenerator.getObjectVersionsUrl(getRepoServer(), bucketName, key));
    return executeRequest(request, ErrorType.ErrorFetchingObjectVersions);
  }

  @Override
  public HttpResponse getRepoObjMetaUsingTag(String bucketName, String key, String tag) {
    HttpGet request = new HttpGet(ObjectUrlGenerator.getGetObjMetaUsingTagUrl(getRepoServer(), bucketName, key, tag));
    return executeRequest(request, ErrorType.ErrorFetchingObjectMeta);
  }

  @Override
  public HttpResponse deleteRepoObjUsingVersionCks(String bucketName, String key, String versionChecksum) {
    HttpDelete request = new HttpDelete(ObjectUrlGenerator.getDeleteObjectVersionCksUrl(getRepoServer(), bucketName, key, versionChecksum));
    return executeRequest(request, ErrorType.ErrorDeletingObject);
  }

  @Override
  public HttpResponse deleteRepoObjUsingVersionNumber(String bucketName, String key, int versionNumber) {
    HttpDelete request = new HttpDelete(ObjectUrlGenerator.getDeleteObjectVersionNumUrl(getRepoServer(), bucketName, key, versionNumber));
    return executeRequest(request, ErrorType.ErrorDeletingObject);
  }

  @Override
  public HttpResponse createRepoObj(String bucketName, RepoObject repoObject, String contentType) {
    HttpPost request = new HttpPost(ObjectUrlGenerator.getCreateObjectUrl(getRepoServer()));
    request.setEntity(getObjectEntity(bucketName, repoObject, CreationMethod.NEW, contentType));
    return executeRequest(request, ErrorType.ErrorCreatingObject);
  }

  @Override
  public HttpResponse versionRepoObj(String bucketName, RepoObject repoObject, String contentType) {
    HttpPost request = new HttpPost(ObjectUrlGenerator.getCreateObjectUrl(getRepoServer()));
    request.setEntity(getObjectEntity(bucketName, repoObject, CreationMethod.VERSION, contentType));
    return executeRequest(request, ErrorType.ErrorVersioningObject);
  }

  private HttpEntity getObjectEntity(String bucketName, RepoObject repoObject, CreationMethod creationType, String contentType) {
    MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
    multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

    multipartEntityBuilder.addTextBody("key", repoObject.getKey());
    multipartEntityBuilder.addTextBody("bucketName", bucketName);
    multipartEntityBuilder.addTextBody("create", creationType.toString());
    multipartEntityBuilder.addTextBody("contentType", contentType);
    if (repoObject.getByteContent() != null) {
      multipartEntityBuilder.addBinaryBody("file", repoObject.getByteContent());
    } else {
      multipartEntityBuilder.addBinaryBody("file", repoObject.getFileContent());
    }

    if (repoObject.getDownloadName() != null) {
      multipartEntityBuilder.addTextBody("downloadName", repoObject.getDownloadName());
    }
    if (repoObject.getTimestamp() != null) {
      multipartEntityBuilder.addTextBody("timestamp", repoObject.getTimestamp().toString());
    }
    if (repoObject.getCreationDate() != null) {
      multipartEntityBuilder.addTextBody("creationDateTime", repoObject.getCreationDate().toString());
    }
    if (repoObject.getTag() != null) {
      multipartEntityBuilder.addTextBody("tag", repoObject.getTag());
    }

    return multipartEntityBuilder.build();

  }

  @Override
  public HttpResponse getRedirectURL(String bucketName, String key) {
    HttpGet request = new HttpGet(ObjectUrlGenerator.getLatestObjectUrl(getRepoServer(), bucketName, key));
    request.setHeader("X-Proxy-Capabilities", "reproxy-file");
    return executeRequest(request, ErrorType.ErrorCreatingObject);
  }

  @Override
  public HttpResponse getObjects(String bucketName, int offset, int limit, boolean includeDeleted) {
    HttpGet request = new HttpGet(ObjectUrlGenerator.getGetObjectsUrl(getRepoServer(), bucketName, offset, limit, includeDeleted));
    return executeRequest(request, ErrorType.ErrorFetchingCollection);
  }

  @Override
  public HttpResponse getObjectsUsingTag(String bucketName, int offset, int limit, boolean includeDeleted, String tag) {
    HttpGet request = new HttpGet(ObjectUrlGenerator.getGetObjectsUrl(getRepoServer(), bucketName, offset, limit, includeDeleted, tag));
    return executeRequest(request, ErrorType.ErrorFetchingCollection);
  }

  @Override
  public Logger getLog() {
    return log;
  }

}