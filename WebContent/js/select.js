/* ------使用说明----- */
/*
 添加城市方法：
     添加组：找到id 是 "selectSub"中select标签下，添加option标签 value属性递增，找到 id 是 "selectSub",按照原有格式添加div,其id属性递增
 添加二级傅选矿选项
  复制 id 是 "selectSub" 下任意input标签，粘贴在需要添加的位置。
*/
var grow = $("selectSub").getElementsByTagName("option").length; //组数
var showGrow = 0;//已打开组
var selectCount = 0; //已选数量 
showSelect(showGrow);
var items = $("selectSub").getElementsByTagName("input");
//alert(maxItem);
//var lenMax = 2; 
//alert(1);
function $(o){ //获取对象
 if(typeof(o) == "string")
 return document.getElementById(o);
 return o;
}
function openBg(state){ //遮照打开关闭控制
 if(state == 1)
 {
  $("bg").style.display = "block";
  var h = document.body.offsetHeight > document.documentElement.offsetHeight ? document.body.offsetHeight : document.documentElement.offsetHeight;
 //alert(document.body.offsetHeight);
 //alert(document.documentElement.offsetHeight);
  $("bg").style.height = h + "px";
 }
 else
 {
  $("bg").style.display = "none";
 } 
}
function openSelect(state){ //选择城市层关闭打开控制
 if(state == 1) 
 {
  $("selectItem").style.display = "block";
  $("selectItem").style.left = ($("bg").offsetWidth - $("selectItem").offsetWidth)/2 + "px";
  $("selectItem").style.top = document.body.scrollTop + 100 + "px";  
 }
 else
 {
  $("selectItem").style.display = "none";
 }
}
function showSelect(id){
 for(var i = 0 ; i < grow ;i++)
 {
  $("c0" + i).style.display = "none";
 }
 $("c0" + id).style.display = "block";
 showGrow = id;
}
function open(id,state){ //显示隐藏控制
 if(state == 1)
 $(id).style.display = "block";
 $(id).style.diaplay = "none";
}
function addPreItem(){ 
 $("previewItem").innerHTML = "";
 var len= 0 ;
 for(var i = 0 ; i < items.length ; i++)
 {
  if(items[i].checked == true)
  {
   //len++;
   //if(len > lenMax)
   //{
   // alert("不能超过" + lenMax +"个选项！")
   // return false;
   //}
   var mes = "<input type='checkbox' checked='true' value='"+ items[i].value +"' onclick='copyItem(\"previewItem\",\"previewItem\");same(this);'>" + items[i].value;
   $("previewItem").innerHTML += mes;
   //alert(items[i].value);
  }
 }
}
function makeSure(){
 //alert(1);
 //$("makeSureItem").innerHTML = $("previewItem").innerHTML;
 openBg(0);
 openSelect(0);
 copyItem("previewItem","makeSureItem") 
}
function copyHTML(id1,id2){
 $(id2).innerHTML = $("id1").innerHTML;
}
function copyItem(id1,id2){
 
 var mes = "";
 var items2 = $(id1).getElementsByTagName("input");
 for(var i = 0 ; i < items2.length ; i++)
 {
  if(items2[i].checked == true)
  {
   mes += "<input type='checkbox' checked='true' value='"+ items2[i].value +"' onclick='copyItem(\"" + id2+ "\",\""+ id1 +"\");same(this);'>" + items2[i].value;   
  }
 }
 $(id2).innerHTML = "";
 $(id2).innerHTML += mes;
 //alert($(id2).innerHTML);
}
function same(ck){
 for(var i = 0 ; i < items.length ; i++)
 {
  if(ck.value == items[i].value)
  {
   items[i].checked = ck.checked;
  }
 }
} 
/* 鼠标拖动 */
var oDrag = "";
var ox,oy,nx,ny,dy,dx;
function drag(e,o){
 var e = e ? e : event;
 var mouseD = document.all ? 1 : 0;
 if(e.button == mouseD)
 {
  oDrag = o.parentNode;
  //alert(oDrag.id);
  ox = e.clientX;
  oy = e.clientY;  
 }
}
function dragPro(e){
 if(oDrag != "")
 { 
  var e = e ? e : event;
  //$(oDrag).style.left = $(oDrag).offsetLeft + "px";
  //$(oDrag).style.top = $(oDrag).offsetTop + "px";
  dx = parseInt($(oDrag).style.left);
  dy = parseInt($(oDrag).style.top);
  //dx = $(oDrag).offsetLeft;
  //dy = $(oDrag).offsetTop;
  nx = e.clientX;
  ny = e.clientY;
  $(oDrag).style.left = (dx + ( nx - ox )) + "px";
  $(oDrag).style.top = (dy + ( ny - oy )) + "px";
  ox = nx;
  oy = ny;
 }
}
document.onmouseup = function(){oDrag = "";}
document.onmousemove = function(event){dragPro(event);}