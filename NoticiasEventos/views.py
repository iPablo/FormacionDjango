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

from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework import generics

"""@api_view(['GET', 'POST'])		
def newsItem_list(request, format=None):
	if request.method == "GET":
		x = NewsItem.objects.all()
		serializer = NewsItemSerializer(x, many=True)
		return Response(serializer.data)
	elif request.method == "POST":
		serializer = NewsItemSerializer(data=request.data)
		if serializer.is_valid():
			serializer.save()
			return Response(serializer.data, status=status.HTTP_201_CREATED)
		return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['GET', 'PUT', 'DELETE'])
def newsItem_detail(request, pk, format=None):
	try:
		x = NewsItem.objects.get(pk=pk)
	except NewsItem.DoesNotExist:
		return Response(status=status.HTTP_404_NOT_FOUND)

	if request.method == 'GET':
		serializer = NewsItemSerializer(x)
		return Response(serializer.data)

	elif request.method == 'PUT':
		serializer = NewsItemSerializer(x, data=request.data)
		if serializer.is_valid():
			serializer.save()
			return Response(serializer.data)
		return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

	elif request.method == 'DELETE':
		x.delete()
		return Response(status=status.HTTP_204_NO_CONTENT)
"""

class NewsItemListAPI(generics.ListCreateAPIView):
	queryset = NewsItem.objects.all()
	serializer_class = NewsItemSerializer

class NewsItemDetailAPI(generics.RetrieveUpdateDestroyAPIView):
	queryset = NewsItem.objects.all()
	serializer_class = NewsItemSerializer

class EventListAPI(generics.ListCreateAPIView):
	queryset = Event.objects.all()
	serializer_class = EventSerializer

class EventDetailAPI(generics.RetrieveUpdateDestroyAPIView):
	queryset = Event.objects.all()
	serializer_class = EventSerializer

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
	
		
