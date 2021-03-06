<%-- 
    Document   : homepage
    Created on : 2017-9-5, 17:03:12
    Author     : qiaoys
--%>

<%@page import="net.sf.json.JSONObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="jquery-easyui-1.5.3/themes/default/easyui.css">
        <link rel="stylesheet" type="text/css" href="jquery-easyui-1.5.3/themes/icon.css?<%=Math.random()%>">     
        <script type="text/javascript" src="jquery-easyui-1.5.3/jquery.min.js"></script>
        <script type="text/javascript" src="jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="dr.js?<%=Math.random()%>"></script>
        <script type="text/javascript" src="dp.js?<%=Math.random()%>"></script>
       <script>
         window.onload = function () {     
             $.ajax({
                    type: 'POST',
                    url: 'LoadType',
                    success: function (data) {
                        var b = data.split(",");
                        var x = document.getElementById("magtype");
                        for (var i = 0; i < b.length; i++) {
                            if (b[i] !== "二极铁") {
                                var option = document.createElement("option");
                                option.text = b[i];
                                option.value = b[i];
                                try {
                                    x.add(option, x.options[null]);
                                } catch (e) {
                                    x.add(option, null);
                                }
                            }
                        }
                    }
                });
                $.ajax({
                    type: 'POST',
                    url: 'LoadFamily',
                    success: function (data) {
                        var b = data.split(",");
                        var x = document.getElementById("magfamily");
                        for (var i = 0; i < b.length; i++) {
                            if (b[i] !== "1") {
                                var option = document.createElement("option");
                                option.text = b[i];
                                option.value = b[i];
                                try {
                                    x.add(option, x.options[null]);
                                } catch (e) {
                                    x.add(option, null);
                                }
                            }
                        }
                    }
                });
                $('#design_require').propertygrid('loadData', rowr);
                $('#design_para').propertygrid('loadData', rowp);
               
            };
        </script>
        <title>录入-磁铁信息</title>
        <style type="text/css">       
            .a-upload {
                padding: 4px 10px;
                width: 350px;
                line-height: 20px;
                position: relative;
                cursor: pointer;
                color: #444;
                background: #fafafa;
                background-repeat: repeat-x;    
                border: 1px solid #bbb;
                border-radius: 5px 5px 5px 5px;
                overflow: hidden;
                display: inline-block;
                *display: inline;
                *zoom: 1
            }
            .a-upload  input {
                position: absolute;
                font-size: 100px;
                right: 0;
                top: 0;
                opacity: 0;
                filter: alpha(opacity=0);
                cursor: pointer
            }
            .a-upload:hover {
                color:  #000000;
                background: #eaf2ff;
                border: 1px solid #b7d2ff;
                text-decoration: none
            } 
            label{
                font-size: 16px
            }
        </style>
    </head>
    <body>
        <h2>录入磁铁设计信息</h2>  
        <div class="easyui-panel"   style="height: 820px;padding:20px 60px;" >
            <div style="width: 1000px;margin:0 auto;">
                <form action="NewDesign" method="POST" target="_blank" onsubmit="return submitform();" >
                    <div id="info" >
                        <label for="magtype">磁铁种类: </label> 
                        <select  id="magtype" name="magtype" style="width:15%;height: 25px" >
                            <option value="二极铁">二极铁</option>                            
                        </select>
                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" style="margin-right: 50px" onclick="newtype()">新建种类</a>
                        <label for="magfamily">磁铁型号: </label>
                        <select  id="magfamily" name="magfamily" style="width:15%;height: 25px" >
                            <option value="1">1</option>                            
                        </select>
                        <a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-add'"style="margin-right: 50px" onclick="newfamily()">新建型号</a>
                    </div>
                    <div id="table">
                        <div id="table1" style=" float: left ">
                             <div style="margin:20px 0;"></div>
                            <label> 请输入磁铁设计要求：
                           <div style="margin:5px 0;"></div>
                            <!--                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="getChanges1()">查看修改项</a>                         -->
                            </label>                                               
                            <table id="design_require" name="design_require" class="easyui-propertygrid" style="width:400px" data-options="

                                   method: 'get',
                                   showGroup: true,
                                   scrollbarSize: 0,                                  
                                   columns: mycolumns                           
                                   ">
                            </table>   
                            <div style="margin:5px 0;"></div>
                            <div class="easyui-panel" title="其他信息"  >
                                <div style="margin-bottom:5px;margin-top: 5px">
                                    <input  class="easyui-textbox" id="designed_by" name="designed_by" label="磁铁设计人：（多人请用分号隔开）" labelPosition="top" style="width:100%">
                                </div>
                                <div style="margin-bottom:5px">
                                    <input class="easyui-textbox" id="approved_by" name="approved_by" label="磁铁负责人：（多人请用分号隔开）" labelPosition="top" style="width:100%">
                                </div>
                                <!--                               -->
                                <div style="margin-bottom:5px">
                                    <input class="easyui-textbox" id="remark" name="remark" label="备注" labelPosition="top" style="width:100%">
                                </div>
                            </div>                                                          
                        </div>
                        <div id="table2" style="float: right">
                             <div style="margin:20px 0;"></div>
                            <label>请输入磁铁设计参数：
                                <!--<a href="javascript:void(0)" class="easyui-linkbutton" onclick="getChanges2()">查看修改项</a>-->
                                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="addrow()">新增设计参数</a>
                                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="delrow()">删除设计参数</a>
                                 <div style="margin:5px 0;"></div>
                            </label>
                            <table id="design_para" name="design_para" class="easyui-propertygrid" style="width:400px" data-options="
                                   method: 'get',
                                   showGroup: true,
                                   scrollbarSize: 0,                                   
                                   columns: mycolumns
                                   ">
                            </table>                    
                        </div>
                    </div>
                    <div style="position:relative;top:730px;bottom: 0; left:0;right:0;text-align: center">                    
                        <input style="width:90px; font-size: 14px" class="a-upload" type="submit" value="提交" >
                        <input style="width:90px; font-size: 14px" class="a-upload" type="button" onclick="location='index.html'" value="返回主页" >                        
                        <input type="hidden" id="hd1" name="hd1"/>
                        <input type="hidden" id="hd2" name="hd2"/> 
                        <input type="hidden" id="hd3" name="hd3"/>
                        <input type="hidden" id="hd4" name="hd4"/> 
                        <input type="hidden" id="pplotn" name="pplotn"/>
                        <input type="hidden" id="mplotn" name="mplotn"/>
                    </div>
                </form>
            </div>
            
            <div style="width: 1000px;margin:0 auto">
              
            <div  class="easyui-panel"   style="float:left;width: 400px"> 
<!--            <div  class="easyui-panel"   style=" position:absolute;left:450px; top:710px; width:404px;padding: 5px">  -->
                <span >请上传物理设计文件（PDF格式）</span>  
                <div style="margin:5px 0;"></div>
                <form  id="formId1" action="UploadFile?plottype=0" method="post"  
                       target="hiddenFrameName1" enctype="multipart/form-data">                       
                    <div>                         
                        <input id="pplotId" type="file" class="a-upload" name="pplotName"  
                               onchange="uploadpplot()" /> 
                        <div style="display: none; color: red;" id="errorTip1">未选择文件  
                        </div>  
                        <div style="display: none; color: green;" id="successTip1"></div>  

                    </div> 
                        <img id="img" src="" width="200" height="200" style="display: none;" /> 
                </form>  
                <iframe style="display: none" name='hiddenFrameName1' id="hidden_frame1"></iframe>  

                <span >请上传机械设计文件（PDF格式）</span>  
                <div style="margin:5px 0;"></div>
                <form  id="formId2" action="UploadFile?plottype=1" method="post"  
                       target="hiddenFrameName2" enctype="multipart/form-data">                       
                    <div>                         
                        <input id="mplotId" type="file" class="a-upload" name="mplotName"  
                               onchange="uploadmplot()" /> 
                        <div style="display: none; color: red;" id="errorTip2">未选择文件  
                        </div>  
                        <div style="display: none; color: green;" id="successTip2"></div>  

                    </div> 
                        <img id="img" src="" width="200" height="200" style="display: none;" /> 
                </form>  
                <iframe style="display: none" name='hiddenFrameName2' id="hidden_frame2"></iframe>
</div>
                <div     style="float:right;width: 400px"> </div>
            </div>
        </div>

        <script type="text/javascript">    
           var pplotnum=0;
            var mplotnum=0;
             document.getElementById("hd3").value=0;
             document.getElementById("hd4").value=0;
            function uploadpplot() {
                var names = $("#pplotId").val().split(".");
                if (names[1] !== "pdf") {
                    $("#errorTip1").html("文件格式必须为PDF");
                    $("#errorTip1").show();
                    return;
                }
                $("#formId1").submit();
            }
            function uploadmplot() {
                var names = $("#mplotId").val().split(".");
                if (names[1] !== "pdf") {
                    $("#errorTip2").html("文件格式必须为PDF");
                    $("#errorTip2").show();
                    return;
                }
                $("#formId2").submit();
            }

            function callback1(success, message, url) {
                if (success === false) {
                    $("#errorTip1").html(message + url);
                    $("#errorTip1").show();
                } else {
                    $("#errorTip1").hide();
                    $("#successTip1").html(message + url);
                    $("#successTip1").show();
                    document.getElementById("pplotn").value = url;
                   pplotnum++;
                    //            $("#img").attr("src", "E:/plot/uploads/" + url);  
                    //            $("#img").show();  
                }
            }
            function callback2(success, message, url) {
                if (success === false) {
                    $("#errorTip2").html(message + url);
                    $("#errorTip2").show();
                } else {
                    $("#errorTip2").hide();
                    $("#successTip2").html(message + url);
                    $("#successTip2").show();
                    document.getElementById("mplotn").value = url;
                    mplotnum++;
                    //            $("#img").attr("src", "E:/plot/uploads/" + url);  
                    //            $("#img").show();  
                }
            }
            function submitform() {
                if(pplotnum>1){
                    document.getElementById("hd3").value=1;                    
                }
                  if(mplotnum>1){
                    document.getElementById("hd4").value=1;                    
                }
                var require = $("#design_require").datagrid("getData");
                document.getElementById("hd1").value = JSON.stringify(require);
                var parameter = $("#design_para").datagrid("getData");
                document.getElementById("hd2").value = JSON.stringify(parameter);
                if (JSON.stringify(require).length === 1057 || JSON.stringify(parameter).length === 2048) {
                    alert("设计要求和基本参数未填写");
                    return false;
                } else {
                    var yn = window.confirm("确认提交？");
                    if (yn) {
                        alert("已提交");
                    } else {
                        return false;
                    }
                }
            }


            function newtype()
            {
                var name = window.prompt("新建磁铁类型", "");
                if (name !== null && name !== "")
                {
                    var x = document.getElementById("magtype");
                    var option = document.createElement("option");
                    option.text = name;
                    option.value = name;
                    try {
                        x.add(option, x.options[null]);
                    } catch (e) {
                        x.add(option, null);
                    }
                }
            }
            function newfamily()
            {
                var name = window.prompt("新建磁铁型号", "");
                if (name !== null && name !== "")
                {
                    var x = document.getElementById("magfamily");
                    var option = document.createElement("option");
                    option.text = name;
                    option.value = name;
                    try {
                        x.add(option, x.options[null]);
                    } catch (e) {
                        x.add(option, null);
                    }
                }
            }
            var mycolumns = [[
                    {field: 'name', title: '设计参数', width: 100, sortable: true},
                    {field: 'value', title: '数值', width: 100, resizable: false, formatter: function (value, arr) {
//                            if (value === '\"null\"') {
//                                return '';
//                            } else {
//                                return value;
//                            }
                            var editor = '';
                            if (typeof arr.editor === 'object') {
                                editor = arr.editor.type;
                            } else {
                                editor = arr.editor;
                            }
                            if (editor === "numberbox" && value !== '') {
                                return Number(value);
                            } else
                                return value;
                        }}
                ]];
            function getChanges1() {
                var require = $("#design_require").datagrid("getData");
                document.getElementById("hd1").value = JSON.stringify(require);
                var parameter = $("#design_para").datagrid("getData");
                document.getElementById("hd2").value = JSON.stringify(parameter);
                if (JSON.stringify(require).length === 1057) {
                    alert(JSON.stringify(parameter).length);
                }
//                   
//                    var s = '';
//                    var rows = $('#design_require').propertygrid('getChanges');
//                    for (var i = 0; i < rows.length; i++) {
//                        s += rows[i].name + ':' + rows[i].value + ',';
//                    }
//                    alert(s);
            }
            function getChanges2() {
                var s = '';
                var rows = $('#design_para').propertygrid('getChanges');
                for (var i = 0; i < rows.length; i++) {
                    s += rows[i].name + ':' + rows[i].value + ',';
                }
                alert(s);
            }
            function addrow() {
                var input = window.prompt("新建设计参数,格式：参数名/参数组名/参数类型(text或number)", "property/其他/text");
                var slice = input.split("/");
                var property = slice[0];
                var group = slice[1];
                var type;
                if (slice[2] === "text") {
                    type = "text";
                } else if (slice[2] === "number") {
                    type = "numberbox";
                } else {
                    alert("格式错误");
                }
                var row = {
                    name: property,
                    value: '',
                    group: group,
                    editor: {type: type, options: {precision: 5}}
                };
                $('#design_para').propertygrid('appendRow', row);
            }
            function delrow() {
                var row = $('#design_para').propertygrid('getSelected');
                var index = $('#design_para').propertygrid('getRowIndex', row);
                if (index > 19) {
                    $('#design_para').propertygrid('deleteRow', index);
                } else
                    alert("只能删除自定义参数！");
            }
        </script>  

    </body>
</html>
