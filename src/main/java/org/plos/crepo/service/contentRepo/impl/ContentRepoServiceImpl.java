package org.plos.crepo.service.contentRepo.impl;

import org.apache.http.HttpResponse;
import org.plos.crepo.dao.buckets.ContentRepoBucketsDao;
import org.plos.crepo.exceptions.ContentRepoException;
import org.plos.crepo.model.RepoCollection;
import org.plos.crepo.model.RepoObject;
import org.plos.crepo.service.buckets.CRepoBucketService;
import org.plos.crepo.service.buckets.impl.CRepoBucketServiceImpl;
import org.plos.crepo.service.collections.CRepoCollectionService;
import org.plos.crepo.service.collections.impl.CRepoCollectionServiceImpl;
import org.plos.crepo.service.config.CRepoConfigService;
import org.plos.crepo.service.config.impl.CRepoConfigServiceImpl;
import org.plos.crepo.service.objects.CRepoObjectService;
import org.plos.crepo.service.objects.impl.CRepoObjectsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Service
public class ContentRepoServiceImpl implements CRepoObjectService, CRepoConfigService, CRepoCollectionService, CRepoBucketService {

  private static final Logger log = LoggerFactory.getLogger(ContentRepoServiceImpl.class);

  @Autowired
  private ContentRepoBucketsDao contentRepoDao;

  @Autowired
  private CRepoBucketServiceImpl cRepoBucketService;

  @Autowired
  private CRepoCollectionServiceImpl cRepoCollectionService;

  @Autowired
  private CRepoConfigServiceImpl cRepoConfigService;

  @Autowired
  private CRepoObjectsServiceImpl cRepoObjectService;

  @Autowired
  public ContentRepoServiceImpl(ContentRepoBucketsDao contentRepoDao,
                                @Value("${crepo.bucketName}") String bucketName) throws Exception {

    this.contentRepoDao = contentRepoDao;

    HttpResponse response = null;
    try {
      response = this.contentRepoDao.getBucket(bucketName);
    } catch(ContentRepoException ce){
      // if it does not exist, create it
      log.debug("The bucket did not exist. Creating the bucket...", ce);
      this.contentRepoDao.createBucket(bucketName);
    }

  }

  public Boolean hasXReproxy() {

    return cRepoConfigService.hasXReproxy();

  }

  @Override
  public Map<String, Object> getRepoConfig() {
    return cRepoConfigService.getRepoConfig();
  }

  @Override
  public Map<String, Object> getRepoStatus() {
    return cRepoConfigService.getRepoStatus();
  }

  public URL[] getRedirectURL(String key){

    return cRepoObjectService.getRedirectURL(key);

  }

  @Override
  public URL[] getRedirectURL(String key, String versionChecksum){

    return cRepoObjectService.getRedirectURL(key, versionChecksum);

  }

  @Override
  public InputStream getLatestAssetInStream(String key){

    return cRepoObjectService.getLatestAssetInStream(key);

  }

  @Override
  public byte[] getLatestAssetByteArray(String key){
   return cRepoObjectService.getLatestAssetByteArray(key);
  }

  @Override
  public InputStream getAssetInStreamUsingVersionCks(String key, String versionChecksum) {

    return cRepoObjectService.getAssetInStreamUsingVersionCks(key, versionChecksum);
  }

  @Override
  public byte[] getAssetByteArrayUsingVersionCks(String key, String versionChecksum) {
    return cRepoObjectService.getAssetByteArrayUsingVersionCks(key, versionChecksum);
  }


  @Override
  public InputStream getAssetInStreamUsingVersionNum(String key, int versionNumber) {

    return cRepoObjectService.getAssetInStreamUsingVersionNum(key, versionNumber);

  }

  @Override
  public byte[] getAssetByteArrayUsingVersionNum(String key, int versionNumber) {
    return cRepoObjectService.getAssetByteArrayUsingVersionNum(key, versionNumber);
  }

  @Override
  public Map<String,Object> getAssetMetaLatestVersion(String key) {
    return cRepoObjectService.getAssetMetaLatestVersion(key);
  }

  @Override
  public Map<String,Object> getAssetMetaUsingVersionChecksum(String key, String versionChecksum) {
    return cRepoObjectService.getAssetMetaUsingVersionChecksum(key, versionChecksum);
  }

  @Override
  public Map<String,Object> getAssetMetaUsingVersionNumber(String key, int versionNumber) {
    return cRepoObjectService.getAssetMetaUsingVersionNumber(key, versionNumber);
  }

  @Override
  public List<Map<String, Object>> getAssetVersionsMeta(String key) {
    return cRepoObjectService.getAssetVersionsMeta(key);
  }


  @Override
  public Boolean deleteLatestAsset(String key) {
    return cRepoObjectService.deleteLatestAsset(key);
  }

  @Override
  public Boolean deleteAssetUsingVersionChecksum(String key, String versionChecksum) {
    return cRepoObjectService.deleteAssetUsingVersionChecksum(key, versionChecksum);
  }

  @Override
  public Boolean deleteAssetUsingVersionNumber(String key, int versionNumber) {
    return cRepoObjectService.deleteAssetUsingVersionNumber(key, versionNumber);
  }

  @Override
  public Map<String, Object> createAsset(RepoObject repoObject) {
    return cRepoObjectService.createAsset(repoObject);
  }

  @Override
  public Map<String, Object> versionAsset(RepoObject repoObject) {
    return cRepoObjectService.versionAsset(repoObject);
  }


  public List<Map<String, Object>> getBuckets(){
    return cRepoBucketService.getBuckets();
  }

  public Map<String, Object> getBucket(String key){
    return cRepoBucketService.getBucket(key);
  }

  public Map<String, Object> createBucket(String key){
    return cRepoBucketService.createBucket(key);
  }

  @Override
  public Map<String, Object> createCollection(RepoCollection repoCollection) {
    return cRepoCollectionService.createCollection(repoCollection);
  }

  @Override
  public Map<String, Object> versionCollection(RepoCollection repoCollection) {
    return cRepoCollectionService.versionCollection(repoCollection);
  }

  @Override
  public Boolean deleteCollectionUsingVersionCks(String key, String versionChecksum) {
    return cRepoCollectionService.deleteCollectionUsingVersionCks(key, versionChecksum);
  }

  @Override
  public Boolean deleteCollectionUsingVersionNumb(String key, int versionNumber) {
    return cRepoCollectionService.deleteCollectionUsingVersionNumb(key, versionNumber);
  }

  @Override
  public Map<String, Object> getCollectionUsingVersionCks(String key, String versionChecksum) {
    return cRepoCollectionService.getCollectionUsingVersionCks(key, versionChecksum);
  }

  @Override
  public Map<String, Object> getCollectionUsingVersionNumber(String key, int versionNumber) {
    return cRepoCollectionService.getCollectionUsingVersionNumber(key, versionNumber);
  }

  @Override
  public Map<String, Object> getCollectionUsingTag(String key, String tag) {
    return cRepoCollectionService.getCollectionUsingTag(key, tag);
  }

  @Override
  public List<Map<String, Object>> getCollectionVersions(String key) {
    return cRepoCollectionService.getCollectionVersions(key);
  }

  @Override
  public List<Map<String, Object>> getCollections(int offset, int limit, boolean includeDeleted, String tag) {
    return cRepoCollectionService.getCollections( offset, limit, includeDeleted, tag);
  }

}
