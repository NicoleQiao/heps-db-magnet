<%-- 
    Document   : newmeas
    Created on : 2018-1-22, 11:16:18
    Author     : qiaoys
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="jquery-easyui-1.5.3/themes/metro/easyui.css">
        <link rel="stylesheet" type="text/css" href="jquery-easyui-1.5.3/themes/icon.css?<%=Math.random()%>">     
        <script type="text/javascript" src="jquery-easyui-1.5.3/jquery.min.js"></script>
        <script type="text/javascript" src="jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="jquery.form.js"></script>    
<!--         <script type="text/javascript" src="dhall.js?"></script>-->
        <style type="text/css">
            label{
                font-size: 16px
            }
        </style>
        <script type="text/javascript">
            window.onload = function () {
                $.ajax({
                    type: 'POST',
                    url: 'LoadType',
                    success: function (data) {
                        var b = data.split(",");
                        var x = document.getElementById("magtype");
                        for (var i = 0; i < b.length; i++) {
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
                });
                $.ajax({
                    type: 'POST',
                    url: 'LoadFamily',
                    success: function (data) {
                        var b = data.split(",");
                        var x = document.getElementById("magfamily");
                        for (var i = 0; i < b.length; i++) {
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
                });
                $('#hall_con').hide();

            };
            $(function () {
                $("#file_form").submit(
                        function () {
                            if(document.getElementById("hd1").value===""){
                                alert("未选择磁铁！");
                                return false;
                            }else{
                            //验证文件格式
                            var fileName = $('#file_input').val();
                            if (fileName === '') {
                                alert('请选择文件');
                                return false;
                            }
                            var fileType = (fileName.substring(fileName
                                    .lastIndexOf(".") + 1, fileName.length))
                                    .toLowerCase();
                            if (fileType !== 'xls' && fileType !== 'xlsx') {
                                alert('文件格式不正确，excel文件！');
                                return false;
                            }
                            $("#file_form").ajaxSubmit({
                                dataType: "json",
                                success: function (data, textStatus) {
                                    if (data['result'] === 'OK') {
                                        alert('上传文件成功');
                                    } else {
                                        alert(data['result']);
                                    }
                                    return false;
                                }
                            });
                            return false;
                        }});
            });
        </script>
        <title>录入-磁测数据</title>
    </head>
    <body>
        <h2>录入磁测数据</h2> 
        <div class="easyui-panel" style="height:820px;padding:10px 60px;position: relative;" > 
            <form id="file_form" action="UpdExcel" enctype="multipart/form-data"
                  method="post">
                <div style="position: absolute;left: 600px">
                    <label for="magtype">磁铁种类：</label> 
                    <select  id="magtype" name="magtype" style="width: 100px; height: 25px" >
                        <option value="none">未选择</option>                                
                    </select>                                            
                    <label for="magfamily">磁铁型号：</label>
                    <select  id="magfamily" name="magfamily" style="width: 100px;height: 25px" >
                        <option value="-1">未选择</option>                                
                    </select>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" style="margin-right: 20px" onclick="chooseMag()">选择磁铁</a>
                </div>
                <div id="showmag" style="position: absolute;top:50px;left: 600px"></div>                       
                <input type="hidden" id="hd1" name="hd1"/>  
                <div style="position: absolute;top:80px;left: 600px"> <label>磁测方法：</label>
                    <input type="radio" name="identity" id="sws" value="sws" onclick="hidehallcon()" checked="checked" /><label for="sws">张力线测磁</label> 
                    <input type="radio" name="identity" id="rcs" value="rcs" onclick="hidehallcon()" /><label for="rcs">旋转螺线圈测磁</label>
                    <input type="radio" name="identity" id="hall" value="hall" onclick="showhallcon()" /><label for="hall">霍尔元件测磁</label>
                </div>  
                <div  name="hall_con" id="hall_con" style="position: absolute;top:110px;left: 600px">                        
                    <input  name="current"class="easyui-numberbox" precision="3" label="测量电流：" labelPosition="before" labelAlign="right" style="width:200px">            
                    <input  name="watergage"class="easyui-numberbox" precision="3"label="测量电压：" labelPosition="before"labelAlign="right" style="width:200px">
                 </div>
                <div style="position: absolute;top:150px;left: 600px">
                    <input name="measdate" class="easyui-datebox" label="磁测时间：" labelPosition="before" labelAlign="right" style="width:200px">
                    <input name="measby"class="easyui-textbox" label="磁测人：" labelPosition="before"  labelAlign="right" style="width:200px" >                      
                </div> 
                <div style="position: absolute;top:190px;left: 600px">                    
                    <input name="measat"class="easyui-textbox" label="磁测地点：" labelPosition="before" labelAlign="right" style="width:200px">            
                    <input name="remark"class="easyui-textbox" label="备注：" labelPosition="before"labelAlign="right" style="width:200px">                    
                </div> 
                <div style="position: absolute;top:240px;left: 600px">                
                    <input type="file" name="file" id="file_input" /> 
                    <input type="submit" value="文件上传" id='upFile-btn'>               
                </div>
            </form>
        </div>
        <div id="dlg" class="easyui-dialog" title="选择磁铁"  style="text-align: center;width:900px;height:500px;padding:10px" data-options="iconCls:'icon-more',closed: true,resizable:true">
            <table id="dg" class="easyui-datagrid"  data-options="singleSelect:true,collapsible:true">
                <thead>
                    <tr>
                        <th data-options="field:'magid',width:70">ID</th>
                        <th data-options="field:'magname',width:100">名称</th>
                        <th data-options="field:'designid',width:100">磁铁设计</th>                
                        <th data-options="field:'weight',width:100">磁铁重量[Kg]</th>
                        <th data-options="field:'series',width:100">生产序号</th>
                        <th data-options="field:'manudate',width:100">生产日期</th>                
                        <th data-options="field:'designedby',width:100">设计单位</th>
                        <th data-options="field:'manuby',width:100">制造单位</th>
                        <th data-options="field:'description',width:98">备注</th>                
                    </tr>
                </thead>
            </table>
            <div style="margin:5px 0;"></div>
            <a href="#" class="easyui-linkbutton" onclick="setMagnet()" data-options="iconCls:'icon-save'">Save</a>
        </div>
<!--        <div id="dlg1" class="easyui-dialog" title="录入霍尔元件测磁测试条件"  style="text-align: center;width:440px;height:500px;padding:10px" data-options="iconCls:'icon-more',closed: true,resizable:true">
            <table id="hall_con1" name="hall_con1" class="easyui-propertygrid" style="width:400px" data-options="
                                   method: 'get',
                                   showGroup: true,
                                   scrollbarSize: 0,                                  
                                   columns: mycolumns                           
                                   ">
                            </table> 
        </div>-->
        <script type="text/javascript">
             function hidehallcon(){                
               $('#hall_con').hide();
            }
            function showhallcon(){
                //$('#hall_con').propertygrid('loadData', rowhall);
               //$('#dlg1').dialog('open');
               $('#hall_con').show();
            }
            function setMagnet() {
                var row = $('#dg').datagrid('getSelected');
                var magname = row.magname;
                var magid = row.magid;
                $('#dlg').dialog('close');
                document.getElementById("showmag").innerHTML = "<font size='3px' color='red' >您已选择：" + magname + "</font>";
                document.getElementById("hd1").value = magid;
            }
            function chooseMag() {
                $.ajax({
                    type: 'POST',
                    url: 'QueryMagnet',
                    scriptCharset: 'UTF-8',
                    data: "magtype=" + document.getElementById("magtype").value + "&magfamily=" + document.getElementById("magfamily").value + "&datemin=" + "" + "&datemax=" + "",
                    success: function (data) {
                        var str = '{"rows":' + data + '}';
                        var s = $.parseJSON(str);
                        $('#dg').datagrid('loadData', s);
                        $('#dlg').dialog('open');
                    }
                });
            }
            //for hidden propertygrid
//            var mycolumns = [[
//                    {field: 'name', title: '设计参数'},
//                    {field: 'value', title: '数值', width: 100, resizable: false, formatter: function (value, arr) {
//                            var editor = '';
//                            if (typeof arr.editor === 'object') {
//                                editor = arr.editor.type;
//                            } else {
//                                editor = arr.editor;
//                            }
//                            if (editor === "numberbox" && value !== '') {
//                                return Number(value);
//                            } else
//                                return value;
//                        }}
//                ]];
        </script>
    </body>
</html>
