from django.shortcuts import render
from .models import NewsItem, Event
from .forms import NewsItemForm, EventForm
from django.views.generic import ListView, DetailView, CreateView, UpdateView, DeleteView
from django.core.urlresolvers import reverse_lazy

# API

from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from rest_framework.renderers import JSONRenderer
from rest_framework.parsers import JSONParser
from .models import NewsItem, Event
from .serializers import NewsItemSerializer, EventSerializer

class JSONResponse(HttpResponse):

	def __init__(self, data, **kwargs):
		content = JSONRenderer().render(data)
		kwargs['content_type'] = 'aplication/json'
		super(JSONResponse, self).__init__(content, **kwargs)

@csrf_exempt		
def newsItem_list(request):
	if request.method == "GET":
		x = NewsItem.objects.all()
		serializer = NewsItemSerializer(x, many=True)
		return JSONResponse(serializer.data)
	elif request.method == "POST":
		data = JSONParser().parse(request)
		serializer = NewsItemSerializer(data=data)
		if serializer.is_valid():
			serializer.save()
			return JSONResponse(serializer.data, status=201)
		return JSONResponse(serializer.errors, status=400)

@csrf_exempt
def function():
	pass

# INDEX

def index(request):
	return render(request, 'NoticiasEventos/index.html', {})

#
# COMIENZO V1
# 

def v1List(request):
	result  = NewsItem.objects.all()
	
	return render(request, 'NoticiasEventos/v1List.html', {'result': result})

def v1Create(request):
	if request.method == "POST":
		form = NewsItemForm(request.POST)

		if form.is_valid():
			post = form.save()
			post.save()

			result = NewsItem.objects.all()
			return render(request, 'NoticiasEventos/v1List.html', {'result': result})
	else:
		form = NewsItemForm()

	return render(request, 'NoticiasEventos/v1Create.html', {'form': form})

def v1Update(request, pk):
	if request.method == "GET":
		x = NewsItem.objects.get(pk=pk)
		return render(request, 'NoticiasEventos/v1Update.html', {'inputs': x})

	if request.method == "POST":
		x = NewsItem.objects.get(pk=pk)
		x.title = request.POST["title"]
		x.description = request.POST["description"]
		x.publish_date = request.POST["publish_date"]
		x.save()

		result  = NewsItem.objects.all()
		return render(request, 'NoticiasEventos/v1List.html', {'result': result})

def v1Delete(request, pk):
	x = NewsItem.objects.get(pk=pk)
	x.delete()

	result  = NewsItem.objects.all()
	return render(request, 'NoticiasEventos/v1List.html', {'result': result})

#
# FIN V1
# 

#
# COMIENZO V2
# 

class v2List(ListView):
	model = NewsItem
	context_object_name = "result"
	template_name = "NoticiasEventos/v2List.html"

class v2Create(CreateView):
	model = NewsItem
	success_url = reverse_lazy('NoticiasEventos:v2List')
	fields = ['title', 'description', 'publish_date']
	template_name = "NoticiasEventos/v1Create.html"

class v2Update(UpdateView):
	model = NewsItem
	success_url = reverse_lazy('NoticiasEventos:v2List')
	fields = ['title', 'description', 'publish_date']
	template_name = "NoticiasEventos/v1Create.html"
		
class v2Delete(DeleteView):
	model = NewsItem
	success_url = reverse_lazy('NoticiasEventos:v2List')

#
# FIN V2
# 

#
# COMIENZO EVENT
# 

class EventList(ListView):
	model = Event
	context_object_name = "result"
	template_name = "NoticiasEventos/eventList.html"

class EventCreate(CreateView):
	model = Event
	success_url = reverse_lazy('NoticiasEventos:eventList')
	fields = ['title', 'description', 'start_date', 'end_date']
	template_name = "NoticiasEventos/eventCreate.html"

class EventUpdate(UpdateView):
	model = Event
	success_url = reverse_lazy('NoticiasEventos:eventList')
	fields = ['title', 'description', 'start_date', 'end_date']
	template_name = "NoticiasEventos/eventCreate.html"

class EventDelete(DeleteView):
	model = Event
	success_url = reverse_lazy('NoticiasEventos:eventList')
		
#
# FIN EVENT
# 
	
		
