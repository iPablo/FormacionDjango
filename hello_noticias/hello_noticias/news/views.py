from django.http import HttpResponseRedirect
from django.shortcuts import render_to_response
from django.template import RequestContext

from django.core.urlresolvers import reverse

from hello_noticias.news.forms import NewsForm
from hello_noticias.news.models import News


# Create your views here.


def create(request):
    data = None
    if request.method == 'POST':
        data = request.POST
    form = NewsForm(data=data)
    if form.is_valid():
        form.save()
        # return render_to_response('news/succes.html',
        #  context_instance=RequestContext(request))
        return HttpResponseRedirect(reverse('succes_create'))
    return render_to_response('news/create.html',
                              {'form': form},
                              context_instance=RequestContext(request))


def update_get(request):
    return render_to_response('news/update_get.html',
                              context_instance=RequestContext(request))


# to do
def update(request, id_new):
    data = None
    if request.method == 'POST':
        data = request.POST
    my_new = News.objects.get(id=id_new)
    form = NewsForm(data=data, instance=my_new)
    if form.is_valid():
        form.save()
        return HttpResponseRedirect(reverse('update_done'))
    return render_to_response('news/update.html',
                              {'form': form},
                              context_instance=RequestContext(request))


def succes_create(request):
    return render_to_response('news/succes.html',
                              context_instance=RequestContext(request))


def update_done(request):
    return render_to_response('news/update_done.html',
                              context_instance=RequestContext(request))


def index(request):
    return render_to_response('news/news.html',
                              context_instance=RequestContext(request))
