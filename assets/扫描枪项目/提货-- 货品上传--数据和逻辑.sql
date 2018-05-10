

--- 数据库链接帐号：
--:我开好发给你



提货 -- 货品上传，插入到 表  bs_receiptDetail ，比对逻辑是：
select top 1 @pOrderID = billno from bs_orderDetail 
where goodsid + isnull(lot1,'') = @pCargoID 
-- @pCargoID 就是扫描的条码，也就是说，条码  = 货号+批次号;

上传完成之后，验证结果：
	select *  from bs_orderDetail 
	where billno = '11203630';




POST man 测试数据：
http://47.97.207.208/apiService/RFService.asmx
--------------------------------------------------
POST /RFWebService/RFService.asmx HTTP/1.1
Host: 47.97.207.208
Content-Type: text/xml
Cache-Control: no-cache
Postman-Token: f29908de-192c-46bb-a4fa-b17dc3fdb678

<?xml version="1.0" encoding="utf-8"?>
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Header>
    <Authentication xmlns="http://tempuri.org/">
      <AccessKey>string</AccessKey>
      <Device>string</Device>
    </Authentication>
  </soap:Header>
  <soap:Body>
    <PodUpload xmlns="http://tempuri.org/">
      <para>
        <Consignee>签收客户名字</Consignee>
        <SignOrg>签收机构</SignOrg>
        <Telephone>电话</Telephone>
        <Cellphone>客户电话</Cellphone>
        <DeliverStatus>1</DeliverStatus>
        <CrtBillNo>11203630</CrtBillNo>
        <DvcID>631008030699</DvcID>
        <MsgList>
          <PodScanMsgEty>
            <OrderID>11203630</OrderID>
            <CargoID>052357DN3006348157</CargoID>   
            --> 这里扫描的条码 按照 : select *  from bs_orderDetail  where billno = '11203630' 查询结果 中 goodsid + lot1 比对的
            <Count>1</Count>
            <Rmk>备注</Rmk>
            <UserID>admin</UserID>
            <ScanTime>2018-05-10T07:21:17</ScanTime>
          </PodScanMsgEty>
          <PodScanMsgEty>
            <OrderID>11203630</OrderID>
            <CargoID>052358DN3004347298</CargoID>
            <Count>1</Count>
            <Rmk>备注</Rmk>
            <UserID>admin</UserID>
            <ScanTime>2018-05-10T07:21:13</ScanTime>
          </PodScanMsgEty>
        </MsgList>
      </para>
    </PodUpload>
  </soap:Body>
</soap:Envelope>


返回：
<?xml version="1.0" encoding="utf-8"?>
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
    <soap:Body>
        <PodUploadResponse xmlns="http://tempuri.org/">
            <PodUploadResult>
                <Rst>
                    <Flag>1</Flag>
                    <Msg />
                </Rst>
            </PodUploadResult>
        </PodUploadResponse>
    </soap:Body>
</soap:Envelope>
