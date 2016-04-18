"""Views.py"""
from django.shortcuts import render, render_to_response, redirect
from .models import NewsItem, Event
from .forms import NewsItemForm, EventForm
from django.views.generic import ListView, CreateView, UpdateView, DeleteView
from django.core.urlresolvers import reverse_lazy
from .serializers import NewsItemSerializer, EventSerializer, UserSerializer
from rest_framework import generics, permissions
from django.contrib.auth.models import User
from .permissions import IsOwnerOrReadOnly
from pure_pagination import Paginator, EmptyPage, PageNotAnInteger
from pure_pagination.mixins import PaginationMixin
# API


class UserList(generics.ListAPIView):
    """Class UserList"""

    queryset = User.objects.all()
    serializer_class = UserSerializer

    def perform_create(self, serializer):
        serializer.save(owner=self.request.user)


class UserDetail(generics.RetrieveAPIView):
    """Class UserDetail"""

    queryset = User.objects.all()
    serializer_class = UserSerializer


class NewsItemListAPI(generics.ListCreateAPIView):
    """Class NewsItemListAPI"""

    queryset = NewsItem.objects.all()
    serializer_class = NewsItemSerializer
    permission_classes = (permissions.IsAuthenticatedOrReadOnly,
                          IsOwnerOrReadOnly)


class NewsItemDetailAPI(generics.RetrieveUpdateDestroyAPIView):
    """Class NewsItemDetailAPIAPI"""

    queryset = NewsItem.objects.all()
    serializer_class = NewsItemSerializer
    permission_classes = (permissions.IsAuthenticatedOrReadOnly,
                          IsOwnerOrReadOnly)


class EventListAPI(generics.ListCreateAPIView):
    """Class EventListAPI"""

    queryset = Event.objects.all()
    serializer_class = EventSerializer
    permission_classes = (permissions.IsAuthenticatedOrReadOnly,
                          IsOwnerOrReadOnly)


class EventDetailAPI(generics.RetrieveUpdateDestroyAPIView):
    """Class EventDetailAPI"""

    queryset = Event.objects.all()
    serializer_class = EventSerializer
    permission_classes = (permissions.IsAuthenticatedOrReadOnly,
                          IsOwnerOrReadOnly)

# INDEX


def index(request):
    """INDEX"""
    return render(request, 'NoticiasEventos/index.html', {})

# V1


def v1List(request):
    """v1List"""
    try:
        page = request.GET.get('page', 1)
    except PageNotAnInteger:
        page = 1

    p = Paginator(NewsItem.objects.all(), 2)
    result = p.page(page)
    return render_to_response('NoticiasEventos/v1List.html',
                              {'result': result})


def v1Create(request):
    """v1Create."""
    if request.method == "POST":
        form = NewsItemForm(request.POST)

        if form.is_valid():
            post = form.save()
            post.save()

            return redirect('/v1/')
    else:
        form = NewsItemForm()

    return render(request, 'NoticiasEventos/v1Create.html', {'form': form})


def v1Update(request, pk):
    """v1Update"""
    if request.method == "GET":
        x = NewsItem.objects.get(pk=pk)
        return render(request, 'NoticiasEventos/v1Update.html', {'inputs': x})

    if request.method == "POST":
        x = NewsItem.objects.get(pk=pk)
        x.title = request.POST["title"]
        x.description = request.POST["description"]
        x.publish_date = request.POST["publish_date"]
        x.save()

        return redirect('/v1/')


def v1Delete(request, pk):
    """v1Delete"""
    x = NewsItem.objects.get(pk=pk)
    x.delete()

    return redirect('/v1/')

# V2


class v2List(PaginationMixin, ListView):
    """Class v2List"""

    paginate_by = 2
    model = NewsItem
    context_object_name = "result"
    template_name = "NoticiasEventos/v2List.html"


class v2Create(CreateView):
    """Class v2Create"""

    model = NewsItem
    success_url = reverse_lazy('NoticiasEventos:v2List')
    fields = ['title', 'description', 'publish_date', 'owner']
    # form_class = NewsItemForm
    template_name = "NoticiasEventos/v1Create.html"


class v2Update(UpdateView):
    """Class v2Update"""

    model = NewsItem
    success_url = reverse_lazy('NoticiasEventos:v2List')
    fields = ['title', 'description', 'publish_date', 'owner']
    template_name = "NoticiasEventos/v1Create.html"


class v2Delete(DeleteView):
    """Class v2Delete"""

    model = NewsItem
    success_url = reverse_lazy('NoticiasEventos:v2List')

# EVENT


class EventList(PaginationMixin, ListView):
    """Class EventList"""

    paginate_by = 2
    model = Event
    context_object_name = "result"
    template_name = "NoticiasEventos/eventList.html"


class EventCreate(CreateView):
    """Class EventCreate"""

    model = Event
    success_url = reverse_lazy('NoticiasEventos:eventList')
    fields = ['title', 'description', 'start_date', 'end_date', 'owner']
    template_name = "NoticiasEventos/eventCreate.html"


class EventUpdate(UpdateView):
    """Class EventUpdate"""

    model = Event
    success_url = reverse_lazy('NoticiasEventos:eventList')
    fields = ['title', 'description', 'start_date', 'end_date', 'owner']
    template_name = "NoticiasEventos/eventCreate.html"


class EventDelete(DeleteView):
    """Class EventDelete"""

    model = Event
    success_url = reverse_lazy('NoticiasEventos:eventList')
