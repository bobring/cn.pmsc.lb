
package com.pmsc.warning.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.pmsc.warning.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SQLException_QNAME = new QName("http://service.warning.pmsc.com/", "SQLException");
    private final static QName _FileNotFoundException_QNAME = new QName("http://service.warning.pmsc.com/", "FileNotFoundException");
    private final static QName _ListFilesByWarnIdResponse_QNAME = new QName("http://service.warning.pmsc.com/", "listFilesByWarnIdResponse");
    private final static QName _ListWarnCap_QNAME = new QName("http://service.warning.pmsc.com/", "ListWarnCap");
    private final static QName _ListFilesByTopResponse_QNAME = new QName("http://service.warning.pmsc.com/", "listFilesByTopResponse");
    private final static QName _GetMaxWarnIdResponse_QNAME = new QName("http://service.warning.pmsc.com/", "getMaxWarnIdResponse");
    private final static QName _ListWarnCapResponse_QNAME = new QName("http://service.warning.pmsc.com/", "ListWarnCapResponse");
    private final static QName _ListFilesByElementResponse_QNAME = new QName("http://service.warning.pmsc.com/", "listFilesByElementResponse");
    private final static QName _DownloadResponse_QNAME = new QName("http://service.warning.pmsc.com/", "downloadResponse");
    private final static QName _Download_QNAME = new QName("http://service.warning.pmsc.com/", "download");
    private final static QName _ListFilesByElement_QNAME = new QName("http://service.warning.pmsc.com/", "listFilesByElement");
    private final static QName _ListWarnCapByElement_QNAME = new QName("http://service.warning.pmsc.com/", "listWarnCapByElement");
    private final static QName _ListWarnCapByElementResponse_QNAME = new QName("http://service.warning.pmsc.com/", "listWarnCapByElementResponse");
    private final static QName _ListFilesByTop_QNAME = new QName("http://service.warning.pmsc.com/", "listFilesByTop");
    private final static QName _ListFilesResponse_QNAME = new QName("http://service.warning.pmsc.com/", "listFilesResponse");
    private final static QName _ListFiles_QNAME = new QName("http://service.warning.pmsc.com/", "listFiles");
    private final static QName _ListFilesByWarnId_QNAME = new QName("http://service.warning.pmsc.com/", "listFilesByWarnId");
    private final static QName _GetMaxWarnId_QNAME = new QName("http://service.warning.pmsc.com/", "getMaxWarnId");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.pmsc.warning.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListFilesByWarnId }
     * 
     */
    public ListFilesByWarnId createListFilesByWarnId() {
        return new ListFilesByWarnId();
    }

    /**
     * Create an instance of {@link ListFiles }
     * 
     */
    public ListFiles createListFiles() {
        return new ListFiles();
    }

    /**
     * Create an instance of {@link GetMaxWarnId }
     * 
     */
    public GetMaxWarnId createGetMaxWarnId() {
        return new GetMaxWarnId();
    }

    /**
     * Create an instance of {@link Download }
     * 
     */
    public Download createDownload() {
        return new Download();
    }

    /**
     * Create an instance of {@link ListFilesByElement }
     * 
     */
    public ListFilesByElement createListFilesByElement() {
        return new ListFilesByElement();
    }

    /**
     * Create an instance of {@link DownloadResponse }
     * 
     */
    public DownloadResponse createDownloadResponse() {
        return new DownloadResponse();
    }

    /**
     * Create an instance of {@link ListFilesResponse }
     * 
     */
    public ListFilesResponse createListFilesResponse() {
        return new ListFilesResponse();
    }

    /**
     * Create an instance of {@link ListWarnCapByElementResponse }
     * 
     */
    public ListWarnCapByElementResponse createListWarnCapByElementResponse() {
        return new ListWarnCapByElementResponse();
    }

    /**
     * Create an instance of {@link ListFilesByTop }
     * 
     */
    public ListFilesByTop createListFilesByTop() {
        return new ListFilesByTop();
    }

    /**
     * Create an instance of {@link ListWarnCapByElement }
     * 
     */
    public ListWarnCapByElement createListWarnCapByElement() {
        return new ListWarnCapByElement();
    }

    /**
     * Create an instance of {@link ListFilesByTopResponse }
     * 
     */
    public ListFilesByTopResponse createListFilesByTopResponse() {
        return new ListFilesByTopResponse();
    }

    /**
     * Create an instance of {@link ListWarnCap }
     * 
     */
    public ListWarnCap createListWarnCap() {
        return new ListWarnCap();
    }

    /**
     * Create an instance of {@link ListFilesByElementResponse }
     * 
     */
    public ListFilesByElementResponse createListFilesByElementResponse() {
        return new ListFilesByElementResponse();
    }

    /**
     * Create an instance of {@link ListWarnCapResponse }
     * 
     */
    public ListWarnCapResponse createListWarnCapResponse() {
        return new ListWarnCapResponse();
    }

    /**
     * Create an instance of {@link GetMaxWarnIdResponse }
     * 
     */
    public GetMaxWarnIdResponse createGetMaxWarnIdResponse() {
        return new GetMaxWarnIdResponse();
    }

    /**
     * Create an instance of {@link FileNotFoundException }
     * 
     */
    public FileNotFoundException createFileNotFoundException() {
        return new FileNotFoundException();
    }

    /**
     * Create an instance of {@link SQLException }
     * 
     */
    public SQLException createSQLException() {
        return new SQLException();
    }

    /**
     * Create an instance of {@link ListFilesByWarnIdResponse }
     * 
     */
    public ListFilesByWarnIdResponse createListFilesByWarnIdResponse() {
        return new ListFilesByWarnIdResponse();
    }

    /**
     * Create an instance of {@link WarnCap }
     * 
     */
    public WarnCap createWarnCap() {
        return new WarnCap();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SQLException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.warning.pmsc.com/", name = "SQLException")
    public JAXBElement<SQLException> createSQLException(SQLException value) {
        return new JAXBElement<SQLException>(_SQLException_QNAME, SQLException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.warning.pmsc.com/", name = "FileNotFoundException")
    public JAXBElement<FileNotFoundException> createFileNotFoundException(FileNotFoundException value) {
        return new JAXBElement<FileNotFoundException>(_FileNotFoundException_QNAME, FileNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListFilesByWarnIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.warning.pmsc.com/", name = "listFilesByWarnIdResponse")
    public JAXBElement<ListFilesByWarnIdResponse> createListFilesByWarnIdResponse(ListFilesByWarnIdResponse value) {
        return new JAXBElement<ListFilesByWarnIdResponse>(_ListFilesByWarnIdResponse_QNAME, ListFilesByWarnIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListWarnCap }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.warning.pmsc.com/", name = "ListWarnCap")
    public JAXBElement<ListWarnCap> createListWarnCap(ListWarnCap value) {
        return new JAXBElement<ListWarnCap>(_ListWarnCap_QNAME, ListWarnCap.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListFilesByTopResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.warning.pmsc.com/", name = "listFilesByTopResponse")
    public JAXBElement<ListFilesByTopResponse> createListFilesByTopResponse(ListFilesByTopResponse value) {
        return new JAXBElement<ListFilesByTopResponse>(_ListFilesByTopResponse_QNAME, ListFilesByTopResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMaxWarnIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.warning.pmsc.com/", name = "getMaxWarnIdResponse")
    public JAXBElement<GetMaxWarnIdResponse> createGetMaxWarnIdResponse(GetMaxWarnIdResponse value) {
        return new JAXBElement<GetMaxWarnIdResponse>(_GetMaxWarnIdResponse_QNAME, GetMaxWarnIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListWarnCapResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.warning.pmsc.com/", name = "ListWarnCapResponse")
    public JAXBElement<ListWarnCapResponse> createListWarnCapResponse(ListWarnCapResponse value) {
        return new JAXBElement<ListWarnCapResponse>(_ListWarnCapResponse_QNAME, ListWarnCapResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListFilesByElementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.warning.pmsc.com/", name = "listFilesByElementResponse")
    public JAXBElement<ListFilesByElementResponse> createListFilesByElementResponse(ListFilesByElementResponse value) {
        return new JAXBElement<ListFilesByElementResponse>(_ListFilesByElementResponse_QNAME, ListFilesByElementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.warning.pmsc.com/", name = "downloadResponse")
    public JAXBElement<DownloadResponse> createDownloadResponse(DownloadResponse value) {
        return new JAXBElement<DownloadResponse>(_DownloadResponse_QNAME, DownloadResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Download }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.warning.pmsc.com/", name = "download")
    public JAXBElement<Download> createDownload(Download value) {
        return new JAXBElement<Download>(_Download_QNAME, Download.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListFilesByElement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.warning.pmsc.com/", name = "listFilesByElement")
    public JAXBElement<ListFilesByElement> createListFilesByElement(ListFilesByElement value) {
        return new JAXBElement<ListFilesByElement>(_ListFilesByElement_QNAME, ListFilesByElement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListWarnCapByElement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.warning.pmsc.com/", name = "listWarnCapByElement")
    public JAXBElement<ListWarnCapByElement> createListWarnCapByElement(ListWarnCapByElement value) {
        return new JAXBElement<ListWarnCapByElement>(_ListWarnCapByElement_QNAME, ListWarnCapByElement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListWarnCapByElementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.warning.pmsc.com/", name = "listWarnCapByElementResponse")
    public JAXBElement<ListWarnCapByElementResponse> createListWarnCapByElementResponse(ListWarnCapByElementResponse value) {
        return new JAXBElement<ListWarnCapByElementResponse>(_ListWarnCapByElementResponse_QNAME, ListWarnCapByElementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListFilesByTop }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.warning.pmsc.com/", name = "listFilesByTop")
    public JAXBElement<ListFilesByTop> createListFilesByTop(ListFilesByTop value) {
        return new JAXBElement<ListFilesByTop>(_ListFilesByTop_QNAME, ListFilesByTop.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListFilesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.warning.pmsc.com/", name = "listFilesResponse")
    public JAXBElement<ListFilesResponse> createListFilesResponse(ListFilesResponse value) {
        return new JAXBElement<ListFilesResponse>(_ListFilesResponse_QNAME, ListFilesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListFiles }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.warning.pmsc.com/", name = "listFiles")
    public JAXBElement<ListFiles> createListFiles(ListFiles value) {
        return new JAXBElement<ListFiles>(_ListFiles_QNAME, ListFiles.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListFilesByWarnId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.warning.pmsc.com/", name = "listFilesByWarnId")
    public JAXBElement<ListFilesByWarnId> createListFilesByWarnId(ListFilesByWarnId value) {
        return new JAXBElement<ListFilesByWarnId>(_ListFilesByWarnId_QNAME, ListFilesByWarnId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMaxWarnId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.warning.pmsc.com/", name = "getMaxWarnId")
    public JAXBElement<GetMaxWarnId> createGetMaxWarnId(GetMaxWarnId value) {
        return new JAXBElement<GetMaxWarnId>(_GetMaxWarnId_QNAME, GetMaxWarnId.class, null, value);
    }

}
